

package com.hoaianh.smsfilter.utils;

/**
 * Created by Nhat on 12/13/17.
 */


public final class AppConstants {

    public static final int API_STATUS_CODE_LOCAL_ERROR = 0;

    public static final String DB_NAME = "sms_filter.db";
    public static final String PREF_NAME = "sms_filter_pref";
    public static final String FIREBASE_CHILD_NAME_MESSAGES = "messages_data";
    public static final String FIREBASE_CHILD_NAME_USER_ID = "userid";

    public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm";

    private AppConstants() {
        // This utility class is not publicly instantiable
    }
}
