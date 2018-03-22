package com.hoaianh.smsfilter.dagger.module;

import android.content.BroadcastReceiver;

import dagger.Module;

/**
 * Created by Nhat on 3/4/18.
 */

@Module
public class BroadcastReceiverModule {

    private final BroadcastReceiver receiver;

    public BroadcastReceiverModule(BroadcastReceiver receiver) {
        this.receiver = receiver;
    }
}
