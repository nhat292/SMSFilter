
package com.hoaianh.smsfilter.dagger.component;

import com.hoaianh.smsfilter.dagger.PerService;
import com.hoaianh.smsfilter.dagger.module.ServiceModule;
import com.hoaianh.smsfilter.service.SyncService;

import dagger.Component;

/**
 * Created by Nhat on 12/13/17.
 */

@PerService
@Component(dependencies = ApplicationComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {

    void inject(SyncService service);

}
