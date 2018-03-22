
package com.hoaianh.smsfilter.dagger.compunent;

import com.hoaianh.smsfilter.common.dagger.component.ApplicationComponent;
import com.hoaianh.smsfilter.dagger.module.ApplicationTestModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Nhat on 12/13/17.
 */
@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends ApplicationComponent {
}
