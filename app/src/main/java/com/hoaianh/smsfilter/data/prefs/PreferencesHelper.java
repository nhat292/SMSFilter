
package com.hoaianh.smsfilter.data.prefs;


/**
 * Created by Nhat on 12/13/17.
 */


public interface PreferencesHelper {

    boolean isFirstTime();

    void setLastSync(long time);

    long getLastSync();
}
