
package com.hoaianh.smsfilter.data.network;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;

/**
 * Created by Nhat on 12/13/17.
 */


@Singleton
public class AppApiHelper implements ApiHelper {

    private ApiHeader mApiHeader;
    private OkHttpClient mOkHttpClient;

    @Inject
    public AppApiHelper(ApiHeader apiHeader, OkHttpClient okHttpClient) {
        mApiHeader = apiHeader;
        mOkHttpClient = okHttpClient;
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHeader;
    }

}

