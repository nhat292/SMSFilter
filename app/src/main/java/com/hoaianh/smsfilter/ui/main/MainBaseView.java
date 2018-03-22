package com.hoaianh.smsfilter.ui.main;

import com.hoaianh.smsfilter.data.db.model.dao.Message;
import com.hoaianh.smsfilter.ui.base.BaseView;

import java.util.List;

/**
 * Created by Nhat on 3/4/18.
 */

public interface MainBaseView extends BaseView {

    void onLoadMessagesSuccess(List<Message> messages);

    void onSaveMessageToFirebaseSuccess();

    void onGetLastSyncSuccess(long time);

    void onDeleteMessageSuccess();
}
