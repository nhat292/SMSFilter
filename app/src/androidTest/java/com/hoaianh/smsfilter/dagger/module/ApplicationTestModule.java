/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.hoaianh.smsfilter.dagger.module;

import android.app.Application;
import android.content.Context;

import com.hoaianh.smsfilter.network.BuildConfig;
import com.hoaianh.smsfilter.network.R;
import com.hoaianh.smsfilter.common.dagger.ApiInfo;
import com.hoaianh.smsfilter.common.dagger.ApplicationContext;
import com.hoaianh.smsfilter.common.dagger.DatabaseInfo;
import com.hoaianh.smsfilter.common.dagger.PreferenceInfo;
import com.hoaianh.smsfilter.common.data.AppDataManager;
import com.hoaianh.smsfilter.common.data.DataManager;
import com.hoaianh.smsfilter.common.data.db.AppDbHelper;
import com.hoaianh.smsfilter.common.data.db.DbHelper;
import com.hoaianh.smsfilter.common.data.prefs.AppPreferencesHelper;
import com.hoaianh.smsfilter.common.data.prefs.PreferencesHelper;
import com.hoaianh.smsfilter.network.ApiHeader;
import com.hoaianh.smsfilter.network.ApiHelper;
import com.hoaianh.smsfilter.network.AppApiHelper;
import com.hoaianh.smsfilter.common.utils.AppConstants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Nhat on 12/13/17.
 */
@Module
public class ApplicationTestModule {

    private final Application mApplication;

    public ApplicationTestModule(Application application) {
        mApplication = application;
    }

    @Provides
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    String provideDatabaseName() {
        return AppConstants.DB_NAME;
    }

    @Provides
    String provideApiKey() {
        return BuildConfig.API_KEY;
    }

    @Provides
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    // TODO : Mock all below for UI testing

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
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
    ApiHeader.ProtectedApiHeader provideProtectedApiHeader(String apiKey,
                                                           PreferencesHelper preferencesHelper) {
        return new ApiHeader.ProtectedApiHeader(
                apiKey,
                preferencesHelper.getCurrentUserId(),
                preferencesHelper.getAccessToken());
    }

    @Provides
    @Singleton
    CalligraphyConfig provideCalligraphyDefaultConfig() {
        return new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/source-sans-pro/SourceSansPro-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
    }
}
