package com.hoaianh.smsfilter.ui.message;

import com.androidnetworking.error.ANError;
import com.hoaianh.smsfilter.data.DataManager;
import com.hoaianh.smsfilter.ui.base.BasePresenter;
import com.hoaianh.smsfilter.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Nhat on 3/4/18.
 */

public class MessagePresenter<V extends MessageBaseView> extends BasePresenter<V> implements MessageMvpPresenter<V> {

    @Inject
    public MessagePresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void getMessages(String senderAddress) {
        getCompositeDisposable().add(getDataManager()
                .getMessages(senderAddress)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(messages -> {
                    getMvpView().onLoadMessagesSuccess(messages);
                }, throwable -> {
                    if (!isViewAttached()) {
                        return;
                    }
                    getMvpView().hideLoading();
                    if (throwable instanceof ANError) {
                        ANError anError = (ANError) throwable;
                        handleApiError(anError);
                    }
                }));
    }
}
