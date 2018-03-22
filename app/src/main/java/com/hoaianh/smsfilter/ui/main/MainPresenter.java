package com.hoaianh.smsfilter.ui.main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hoaianh.smsfilter.App;
import com.hoaianh.smsfilter.R;
import com.hoaianh.smsfilter.data.DataManager;
import com.hoaianh.smsfilter.data.db.model.dao.Message;
import com.hoaianh.smsfilter.ui.base.BasePresenter;
import com.hoaianh.smsfilter.utils.AppConstants;
import com.hoaianh.smsfilter.utils.CommonUtils;
import com.hoaianh.smsfilter.utils.NetworkUtils;
import com.hoaianh.smsfilter.utils.rx.SchedulerProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Nhat on 3/4/18.
 */

public class MainPresenter<V extends MainBaseView> extends BasePresenter<V> implements MainMvpPresenter<V> {

    private final DatabaseReference mDatabase;

    @Inject
    public MainPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void getMessages() {
        getMvpView().showLoading();
        List<Message> messages = new ArrayList<>();
        Uri smsUri = Uri.parse("content://sms/");
        Cursor cur = App.getInstance().getContentResolver().query(smsUri, null, null, null, null);
        if (cur != null) {
            while (cur.moveToNext()) {
                String address = cur.getString(cur.getColumnIndex("address"));
                String body = cur.getString(cur.getColumnIndexOrThrow("body"));
                String date = cur.getString(cur.getColumnIndexOrThrow("date"));
                String type = cur.getString(cur.getColumnIndexOrThrow("type")).contains("1") ? Message.TYPE_RECEIVE : Message.TYPE_SEND;

                Message message = new Message();
                message.setAddress(address);
                message.setBody(body);
                message.setDate(date);
                message.setType(type);
                message.setIsSync(false);

                messages.add(message);
            }
            cur.close();
            getCompositeDisposable().add(getDataManager()
                    .saveMessages(messages)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(aBoolean -> {
                        getMvpView().hideLoading();
                        getLocalMessages();
                    }, throwable -> {
                        if (!isViewAttached()) {
                            return;
                        }
                        getMvpView().hideLoading();
                        if (throwable instanceof ANError) {
                            ANError anError = (ANError) throwable;
                            handleApiError(anError);
                        }
                    }));
        }
    }

    @Override
    public void saveMessagesToFirebase() {
        if (!NetworkUtils.isNetworkConnected(App.getInstance())) {
            getMvpView().onError(R.string.connection_error);
            return;
        }
        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .getMessages()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(messages -> {
                    for (Message message : messages) {
                        if (message.getIsSync()) {
                            continue;
                        }
                        Map<String, Object> childObject = new HashMap<>();
                        childObject.put("id", message.getId());
                        childObject.put("address", message.getAddress());
                        childObject.put("body", message.getBody());
                        childObject.put("date", message.getDate());
                        childObject.put("type", message.getType());
                        mDatabase.child(AppConstants.FIREBASE_CHILD_NAME_MESSAGES).push().updateChildren(childObject, (databaseError, databaseReference) -> {
                            if (databaseError != null) {
                                System.out.println("Data could not be saved " + databaseError.getMessage());
                            } else {
                                System.out.println("Data saved successfully.");
                            }
                        });
                    }

                    getMvpView().hideLoading();
                    long time = System.currentTimeMillis();
                    getDataManager().setLastSync(time);
                    getMvpView().onGetLastSyncSuccess(time);
                    getMvpView().onSaveMessageToFirebaseSuccess();

                    for (Message message : messages) {
                        message.setIsSync(true);
                    }
                    getDataManager().updateMessages(messages);

                }, throwable -> {
                    if (!isViewAttached()) {
                        return;
                    }
                    getMvpView().hideLoading();
                    if (throwable instanceof ANError) {
                        ANError anError = (ANError) throwable;
                        handleApiError(anError);
                    }
                }));
    }

    @Override
    public void downloadMessagesFromFirebase() {
        if (!NetworkUtils.isNetworkConnected(App.getInstance())) {
            getMvpView().onError(R.string.connection_error);
            return;
        }
        getMvpView().showMessage(R.string.message_download_started);
        StringBuilder stringBuilder = new StringBuilder();
        mDatabase.child(AppConstants.FIREBASE_CHILD_NAME_MESSAGES).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mDatabase.child(AppConstants.FIREBASE_CHILD_NAME_MESSAGES).removeEventListener(this);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HashMap<String, Object> object = (HashMap<String, Object>) snapshot.getValue();
                    String address = (String) object.get("address");
                    String body = (String) object.get("body");
                    String date = (String) object.get("date");
                    String type = (String) object.get("type");
                    String dateStr = CommonUtils.showDateTime(new Date(Long.parseLong(date)));

                    stringBuilder.append("ADDRESS: " + address + " || BODY: " + body + " || DATE: " + dateStr + " || TYPE: " + type
                            + "\n\n***\n\n");

                }
                boolean hasPermission = (ContextCompat.checkSelfPermission(App.getInstance(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
                if (hasPermission) {
                    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/sms_filter";
                    File storageDir = new File(path);
                    if (!storageDir.exists() && !storageDir.mkdirs()) {
                        return;
                    }
                    File file = new File(path, "messages_" + CommonUtils.getDateString(new Date()) + ".txt");
                    try {
                        FileOutputStream stream = new FileOutputStream(file);
                        try {
                            stream.write(stringBuilder.toString().getBytes());
                        } finally {
                            stream.close();
                            Toast.makeText(App.getInstance(),
                                    String.format(App.getInstance().getString(R.string.message_saved_to_format),
                                            file.getAbsolutePath()), Toast.LENGTH_LONG).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
                System.out.println("Data downloaded successfully.");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mDatabase.child(AppConstants.FIREBASE_CHILD_NAME_MESSAGES).removeEventListener(this);
            }
        });
    }

    @Override
    public void getLastSync() {
        getMvpView().onGetLastSyncSuccess(getDataManager().getLastSync());
    }

    @Override
    public void deleteAllMessages() {
        if (!NetworkUtils.isNetworkConnected(App.getInstance())) {
            getMvpView().onError(R.string.connection_error);
            return;
        }
        getMvpView().showLoading();
        mDatabase.child(AppConstants.FIREBASE_CHILD_NAME_MESSAGES).removeValue((databaseError, databaseReference) -> {
            getMvpView().hideLoading();
            getMvpView().onDeleteMessageSuccess();
        });
    }

    private void getLocalMessages() {
        getCompositeDisposable().add(getDataManager()
                .getMessagesUniqueSender()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(messages ->
                                getMvpView().onLoadMessagesSuccess(messages)
                        , throwable -> {
                            if (!isViewAttached()) {
                                return;
                            }
                            if (throwable instanceof ANError) {
                                ANError anError = (ANError) throwable;
                                handleApiError(anError);
                            }
                        }));
    }
}
