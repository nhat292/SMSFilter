
package com.hoaianh.smsfilter.dagger;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Nhat on 12/13/17.
 */


@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerService {
}

