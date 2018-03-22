package com.hoaianh.smsfilter.utils.permission;

import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequestErrorListener;

/**
 * Created by Nhat on 11/29/17.
 */

public class ErrorPermissionRequestListener implements PermissionRequestErrorListener {

    private PermissionResultListener listener;

    public ErrorPermissionRequestListener(PermissionResultListener listener) {
        this.listener = listener;
    }

    @Override
    public void onError(DexterError error) {
        listener.onPermissionError(error.toString());
    }
}
