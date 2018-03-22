package com.hoaianh.smsfilter.dagger;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Nhat on 3/4/18.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerBroadCastReceiver {
}
