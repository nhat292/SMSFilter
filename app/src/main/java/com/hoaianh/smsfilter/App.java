
package com.hoaianh.smsfilter;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.interceptors.HttpLoggingInterceptor.Level;
import com.crashlytics.android.Crashlytics;
import com.hoaianh.smsfilter.dagger.component.ApplicationComponent;
import com.hoaianh.smsfilter.dagger.component.DaggerApplicationComponent;
import com.hoaianh.smsfilter.dagger.module.ApplicationModule;
import com.hoaianh.smsfilter.data.DataManager;
import com.hoaianh.smsfilter.ui.launch.LaunchActivity;
import com.hoaianh.smsfilter.utils.AppLogger;

import javax.inject.Inject;

import cat.ereza.customactivityoncrash.config.CaocConfig;
import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


/**
 * Created by Nhat on 12/13/17.
 */


public class App extends Application implements Application.ActivityLifecycleCallbacks {

    private static final String TAG = "App";

    @Inject
    DataManager mDataManager;

    @Inject
    CalligraphyConfig mCalligraphyConfig;

    private ApplicationComponent mApplicationComponent;

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //Fabric crashing tracker
        Fabric.with(this, new Crashlytics());

        //Init application component
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
        mApplicationComponent.inject(this);

        //Debug log
        AppLogger.init();

        AndroidNetworking.initialize(getApplicationContext());
        if (BuildConfig.DEBUG) {
            AndroidNetworking.enableLogging(Level.BODY);
        }

        //Init default font
        CalligraphyConfig.initDefault(mCalligraphyConfig);

        //Custom crashing
        CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //default: CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM
                .trackActivities(true) //default: false
                .minTimeBetweenCrashesMs(2000) //default: 3000
                .errorDrawable(R.mipmap.ic_launcher) //default: bug image
                .restartActivity(LaunchActivity.class) //default: null (your app's launch activity)
                .apply();

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }


    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        AppLogger.d(TAG, activity.getClass().getSimpleName() + ": created");
    }

    @Override
    public void onActivityStarted(Activity activity) {
        AppLogger.d(TAG, activity.getClass().getSimpleName() + ": started");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        AppLogger.d(TAG, activity.getClass().getSimpleName() + ": resumed");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        AppLogger.d(TAG, activity.getClass().getSimpleName() + ": paused");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        AppLogger.d(TAG, activity.getClass().getSimpleName() + ": stopped");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        AppLogger.d(TAG, activity.getClass().getSimpleName() + ": save instance state");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        AppLogger.d(TAG, activity.getClass().getSimpleName() + ": destroyed");
    }

}
