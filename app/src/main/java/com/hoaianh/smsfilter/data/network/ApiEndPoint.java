
package com.hoaianh.smsfilter.data.network;


/**
 * Created by Nhat on 12/13/17.
 */


public final class ApiEndPoint {

    public static String BASE_URL = "";



    public static void changeBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
    }

    private ApiEndPoint() {
        // This class is not publicly instantiable
    }

}
