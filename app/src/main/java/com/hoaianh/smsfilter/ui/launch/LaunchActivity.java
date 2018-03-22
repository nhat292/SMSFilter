package com.hoaianh.smsfilter.ui.launch;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;

import com.hoaianh.smsfilter.R;
import com.hoaianh.smsfilter.ui.base.BaseActivity;
import com.hoaianh.smsfilter.ui.login.LoginActivity;
import com.hoaianh.smsfilter.utils.CommonUtils;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by Nhat on 2/28/18.
 */

public class LaunchActivity extends BaseActivity implements LaunchBaseView {

    private Handler mHandler = new Handler(Looper.getMainLooper());
    private static final int SPLASH_DURATION = 2000;

    @Inject
    LaunchMvpPresenter<LaunchBaseView> mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);

        mHandler.postDelayed(new HandlePassLaunchActivity(this), SPLASH_DURATION);
    }

    @Override
    protected void setUp() {

    }

    @Override
    public void exitApp() {
        finish();
    }

    @Override
    public void openMain() {
        startActivity(LoginActivity.getStartIntent(this));
        exitApp();
    }

    private static class HandlePassLaunchActivity implements Runnable {

        private WeakReference<LaunchActivity> activityWR;

        public HandlePassLaunchActivity(LaunchActivity activity) {
            activityWR = new WeakReference<>(activity);
        }

        @Override
        public void run() {
            LaunchActivity activity = activityWR.get();
            if (activity == null) {
                return;
            }
            activity.mPresenter.checkOpenNextActivity();
        }
    }


}
