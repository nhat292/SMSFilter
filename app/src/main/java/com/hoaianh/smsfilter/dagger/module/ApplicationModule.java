
package com.hoaianh.smsfilter.dagger.module;

import android.app.Application;
import android.content.Context;

import com.hoaianh.smsfilter.BuildConfig;
import com.hoaianh.smsfilter.R;
import com.hoaianh.smsfilter.dagger.ApiInfo;
import com.hoaianh.smsfilter.dagger.ApplicationContext;
import com.hoaianh.smsfilter.dagger.DatabaseInfo;
import com.hoaianh.smsfilter.dagger.PreferenceInfo;
import com.hoaianh.smsfilter.data.AppDataManager;
import com.hoaianh.smsfilter.data.DataManager;
import com.hoaianh.smsfilter.data.db.AppDbHelper;
import com.hoaianh.smsfilter.data.db.DbHelper;
import com.hoaianh.smsfilter.data.network.ApiHeader;
import com.hoaianh.smsfilter.data.network.ApiHelper;
import com.hoaianh.smsfilter.data.network.AppApiHelper;
import com.hoaianh.smsfilter.data.prefs.AppPreferencesHelper;
import com.hoaianh.smsfilter.data.prefs.PreferencesHelper;
import com.hoaianh.smsfilter.utils.AppConstants;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Nhat on 12/13/17.
 */


@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DB_NAME;
    }

    @Provides
    @ApiInfo
    String provideApiKey() {
        return BuildConfig.API_KEY;
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }


    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @Singleton
    ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }

    @Provides
    @Singleton
    ApiHeader.ProtectedApiHeader provideProtectedApiHeader(@ApiInfo String apiKey,
                                                           PreferencesHelper preferencesHelper) {
        return new ApiHeader.ProtectedApiHeader(
                apiKey,
                1L,
                "");
    }

    @Provides
    @Singleton
    CalligraphyConfig provideCalligraphyDefaultConfig() {
        return new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/source-sans-pro/SourceSansPro-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
    }
}
