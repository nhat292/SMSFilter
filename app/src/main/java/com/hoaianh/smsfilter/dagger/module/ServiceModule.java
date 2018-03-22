
package com.hoaianh.smsfilter.dagger.module;

import android.app.Service;

import dagger.Module;

/**
 * Created by Nhat on 12/13/17.
 */


@Module
public class ServiceModule {

    private final Service mService;

    public ServiceModule(Service service) {
        mService = service;
    }
}
