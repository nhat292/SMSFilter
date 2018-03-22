package com.hoaianh.smsfilter.ui.login;

import com.hoaianh.smsfilter.dagger.PerActivity;
import com.hoaianh.smsfilter.ui.base.MvpPresenter;

/**
 * Created by Nhat on 3/6/18.
 */

@PerActivity
public interface LoginMvpPresenter<V extends LoginBaseView> extends MvpPresenter<V> {

    void login(String id);

}
