
package com.hoaianh.smsfilter.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;


/**
 * Created by Nhat on 12/13/17.
 */


public final class AppUtils {

    private AppUtils() {
        // This class is not publicly instantiable
    }

    public static void openPlayStoreForApp(Context context) {
        final String appPackageName = context.getPackageName();
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.onedev.colorc&hl=vi"/*context
                            .getResources()
                            .getString(R.string.app_market_link) + appPackageName*/)));
        } catch (android.content.ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.onedev.colorc&hl=vi"/*context
                            .getResources()
                            .getString(R.string.app_google_play_store_link) + appPackageName*/)));
        }
    }

}
