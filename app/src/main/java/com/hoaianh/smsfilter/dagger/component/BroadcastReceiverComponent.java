
package com.hoaianh.smsfilter.dagger.component;

import com.hoaianh.smsfilter.dagger.PerBroadCastReceiver;
import com.hoaianh.smsfilter.dagger.module.BroadcastReceiverModule;
import com.hoaianh.smsfilter.receiver.IncomingSmsBroadcastReceiver;

import dagger.Component;

/**
 * Created by Nhat on 12/13/17.
 */

@PerBroadCastReceiver
@Component(dependencies = ApplicationComponent.class, modules = BroadcastReceiverModule.class)
public interface BroadcastReceiverComponent {

    void inject(IncomingSmsBroadcastReceiver receiver);

}
