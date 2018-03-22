package com.hoaianh.smsfilter.ui.launch;

import com.hoaianh.smsfilter.dagger.PerActivity;
import com.hoaianh.smsfilter.ui.base.MvpPresenter;

/**
 * Created by Nhat on 3/1/18.
 */

@PerActivity
public interface LaunchMvpPresenter<V extends LaunchBaseView> extends MvpPresenter<V> {

    void checkOpenNextActivity();

}
