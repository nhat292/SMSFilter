package com.hoaianh.smsfilter.ui.main;

import com.hoaianh.smsfilter.dagger.PerActivity;
import com.hoaianh.smsfilter.ui.base.MvpPresenter;

/**
 * Created by Nhat on 3/4/18.
 */

@PerActivity
public interface MainMvpPresenter<V extends MainBaseView> extends MvpPresenter<V> {

    void getMessages();

    void saveMessagesToFirebase();

    void downloadMessagesFromFirebase();

    void getLastSync();

    void deleteAllMessages();

}
