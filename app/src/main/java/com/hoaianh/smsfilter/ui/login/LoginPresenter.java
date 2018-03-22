package com.hoaianh.smsfilter.ui.login;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hoaianh.smsfilter.App;
import com.hoaianh.smsfilter.R;
import com.hoaianh.smsfilter.data.DataManager;
import com.hoaianh.smsfilter.ui.base.BasePresenter;
import com.hoaianh.smsfilter.utils.AppConstants;
import com.hoaianh.smsfilter.utils.NetworkUtils;
import com.hoaianh.smsfilter.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Nhat on 3/6/18.
 */

public class LoginPresenter<V extends LoginBaseView> extends BasePresenter<V> implements LoginMvpPresenter<V> {

    private final DatabaseReference mDatabase;

    @Inject
    public LoginPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void login(String id) {
        if (id.isEmpty()) {
            getMvpView().onLoginError(R.string.message_user_id_empty);
            return;
        }
        if (!NetworkUtils.isNetworkConnected(App.getInstance())) {
            getMvpView().onLoginError(R.string.connection_error);
            return;
        }
        getMvpView().showLoading();
        mDatabase.child(AppConstants.FIREBASE_CHILD_NAME_USER_ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getMvpView().hideLoading();
                mDatabase.child(AppConstants.FIREBASE_CHILD_NAME_USER_ID).removeEventListener(this);
                try {
                    String userId = dataSnapshot.getValue().toString();
                    if (userId.equals(id)) {
                        getMvpView().onLoginSuccess();
                    } else {
                        getMvpView().onLoginError(R.string.message_user_id_is_not_correct);
                    }
                } catch (Exception e) {
                    getMvpView().onLoginError(R.string.api_default_error);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                getMvpView().hideLoading();
                mDatabase.child(AppConstants.FIREBASE_CHILD_NAME_USER_ID).removeEventListener(this);
            }
        });
    }
}
