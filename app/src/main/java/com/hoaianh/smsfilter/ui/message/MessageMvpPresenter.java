package com.hoaianh.smsfilter.ui.message;

import com.hoaianh.smsfilter.dagger.PerActivity;
import com.hoaianh.smsfilter.ui.base.MvpPresenter;

/**
 * Created by Nhat on 3/4/18.
 */

@PerActivity
public interface MessageMvpPresenter<V extends MessageBaseView> extends MvpPresenter<V> {

    void getMessages(String senderAddress);

}
