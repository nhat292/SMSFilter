package com.hoaianh.smsfilter.ui.login;

import android.support.annotation.StringRes;

import com.hoaianh.smsfilter.ui.base.BaseView;

/**
 * Created by Nhat on 3/6/18.
 */

public interface LoginBaseView extends BaseView {

    void onLoginSuccess();

    void onLoginError(@StringRes int resId);
}
