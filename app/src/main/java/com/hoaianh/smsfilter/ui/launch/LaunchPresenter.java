package com.hoaianh.smsfilter.ui.launch;

import com.hoaianh.smsfilter.data.DataManager;
import com.hoaianh.smsfilter.ui.base.BasePresenter;
import com.hoaianh.smsfilter.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Nhat on 3/1/18.
 */

public class LaunchPresenter<V extends LaunchBaseView> extends BasePresenter<V> implements LaunchMvpPresenter<V> {

    @Inject
    public LaunchPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void checkOpenNextActivity() {
        getMvpView().openMain();
    }
}
