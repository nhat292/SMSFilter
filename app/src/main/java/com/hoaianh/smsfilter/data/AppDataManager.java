
package com.hoaianh.smsfilter.data;


import android.content.Context;

import com.hoaianh.smsfilter.dagger.ApplicationContext;
import com.hoaianh.smsfilter.data.db.DbHelper;
import com.hoaianh.smsfilter.data.db.model.dao.Message;
import com.hoaianh.smsfilter.data.network.ApiHeader;
import com.hoaianh.smsfilter.data.network.ApiHelper;
import com.hoaianh.smsfilter.data.prefs.PreferencesHelper;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by Nhat on 12/13/17.
 */


@Singleton
public class AppDataManager implements DataManager {

    private static final String TAG = "AppDataManager";

    private final Context mContext;
    private final PreferencesHelper mPreferencesHelper;
    private final ApiHelper mApiHelper;
    private final DbHelper mDbHelper;

    @Inject
    public AppDataManager(@ApplicationContext Context context,
                          DbHelper dbHelper,
                          PreferencesHelper preferencesHelper,
                          ApiHelper apiHelper) {
        mContext = context;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHelper.getApiHeader();
    }


    @Override
    public Observable<Boolean> saveMessages(List<Message> messages) {
        return mDbHelper.saveMessages(messages);
    }

    @Override
    public Observable<List<Message>> getMessagesUniqueSender() {
        return mDbHelper.getMessagesUniqueSender();
    }

    @Override
    public Observable<List<Message>> getMessages(String senderAddress) {
        return mDbHelper.getMessages(senderAddress);
    }

    @Override
    public Observable<List<Message>> getMessages() {
        return mDbHelper.getMessages();
    }

    @Override
    public Observable<Boolean> saveMessage(Message message) {
        return mDbHelper.saveMessage(message);
    }

    @Override
    public void updateMessages(List<Message> messages) {
        mDbHelper.updateMessages(messages);
    }

    @Override
    public boolean isFirstTime() {
        return mPreferencesHelper.isFirstTime();
    }

    @Override
    public void setLastSync(long time) {
        mPreferencesHelper.setLastSync(time);
    }

    @Override
    public long getLastSync() {
        return mPreferencesHelper.getLastSync();
    }
}
