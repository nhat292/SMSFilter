package com.hoaianh.smsfilter.utils.permission;

import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

/**
 * Created by Nhat on 11/29/17.
 */

public class SinglePermissionListener implements PermissionListener {

    private PermissionResultListener listener;

    public SinglePermissionListener(PermissionResultListener listener) {
        this.listener = listener;
    }


    @Override
    public void onPermissionGranted(PermissionGrantedResponse response) {
        listener.onPermissionGranted(response.getPermissionName());
    }

    @Override
    public void onPermissionDenied(PermissionDeniedResponse response) {
        listener.onPermissionDenied(response.getPermissionName(), response.isPermanentlyDenied());
    }

    @Override
    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
        listener.onPermissionRationale(token);
    }
}
