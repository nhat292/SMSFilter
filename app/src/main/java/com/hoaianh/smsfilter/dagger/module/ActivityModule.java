
package com.hoaianh.smsfilter.dagger.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.hoaianh.smsfilter.dagger.ActivityContext;
import com.hoaianh.smsfilter.dagger.PerActivity;
import com.hoaianh.smsfilter.ui.launch.LaunchBaseView;
import com.hoaianh.smsfilter.ui.launch.LaunchMvpPresenter;
import com.hoaianh.smsfilter.ui.launch.LaunchPresenter;
import com.hoaianh.smsfilter.ui.login.LoginBaseView;
import com.hoaianh.smsfilter.ui.login.LoginMvpPresenter;
import com.hoaianh.smsfilter.ui.login.LoginPresenter;
import com.hoaianh.smsfilter.ui.main.MainBaseView;
import com.hoaianh.smsfilter.ui.main.MainMvpPresenter;
import com.hoaianh.smsfilter.ui.main.MainPresenter;
import com.hoaianh.smsfilter.ui.message.MessageBaseView;
import com.hoaianh.smsfilter.ui.message.MessageMvpPresenter;
import com.hoaianh.smsfilter.ui.message.MessagePresenter;
import com.hoaianh.smsfilter.utils.rx.AppSchedulerProvider;
import com.hoaianh.smsfilter.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Nhat on 12/13/17.
 */


@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @PerActivity
    LaunchMvpPresenter<LaunchBaseView> provideLaunchPresenter(
            LaunchPresenter<LaunchBaseView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    MainMvpPresenter<MainBaseView> provideMainPresenter(
            MainPresenter<MainBaseView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    MessageMvpPresenter<MessageBaseView> provideMessagePresenter(
            MessagePresenter<MessageBaseView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    LoginMvpPresenter<LoginBaseView> provideLoginPresenter(
            LoginPresenter<LoginBaseView> presenter) {
        return presenter;
    }

}
