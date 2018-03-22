
package com.hoaianh.smsfilter.dagger.component;

import android.app.Application;
import android.content.Context;

import com.hoaianh.smsfilter.App;
import com.hoaianh.smsfilter.dagger.ApplicationContext;
import com.hoaianh.smsfilter.dagger.module.ApplicationModule;
import com.hoaianh.smsfilter.data.DataManager;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Nhat on 12/13/17.
 */


@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(App app);

    @ApplicationContext
    Context context();

    Application application();

    DataManager getDataManager();
}