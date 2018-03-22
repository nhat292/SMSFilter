
package com.hoaianh.smsfilter.dagger.component;

import com.hoaianh.smsfilter.dagger.PerActivity;
import com.hoaianh.smsfilter.dagger.module.ActivityModule;
import com.hoaianh.smsfilter.ui.launch.LaunchActivity;
import com.hoaianh.smsfilter.ui.login.LoginActivity;
import com.hoaianh.smsfilter.ui.main.MainActivity;
import com.hoaianh.smsfilter.ui.message.MessageActivity;

import dagger.Component;

/**
 * Created by Nhat on 12/13/17.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(LaunchActivity activity);
    void inject(MainActivity activity);
    void inject(MessageActivity activity);
    void inject(LoginActivity activity);
}
