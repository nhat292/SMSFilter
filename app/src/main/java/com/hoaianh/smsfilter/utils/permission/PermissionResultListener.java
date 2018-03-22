package com.hoaianh.smsfilter.utils.permission;

import com.karumi.dexter.PermissionToken;

/**
 * Created by Nhat on 11/29/17.
 */

public interface PermissionResultListener {

    void onPermissionError(String error);

    void onPermissionGranted(String permissionName);

    void onPermissionDenied(String permissionName, boolean isPermanentDenied);

    void onPermissionRationale(PermissionToken token);
}
