
package com.hoaianh.smsfilter.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.hoaianh.smsfilter.dagger.ApplicationContext;
import com.hoaianh.smsfilter.dagger.PreferenceInfo;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Nhat on 12/13/17.
 */


@Singleton
public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_KEY_USER = "PREF_KEY_FIRST_TIME";
    private static final String PREF_KEY_LAST_SYNC = "PREF_KEY_LAST_SYNC";

    private final SharedPreferences mPrefs;

    @Inject
    public AppPreferencesHelper(@ApplicationContext Context context,
                                @PreferenceInfo String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }


    @Override
    public boolean isFirstTime() {
        boolean firstTime = mPrefs.getBoolean(PREF_KEY_USER, true);
        if (firstTime) {
            mPrefs.edit().putBoolean(PREF_KEY_USER, false).apply();
        }
        return firstTime;
    }

    @Override
    public void setLastSync(long time) {
        mPrefs.edit().putLong(PREF_KEY_LAST_SYNC, time).apply();
    }

    @Override
    public long getLastSync() {
        return mPrefs.getLong(PREF_KEY_LAST_SYNC, 0);
    }
}
