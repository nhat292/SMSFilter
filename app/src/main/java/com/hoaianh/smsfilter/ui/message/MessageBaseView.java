package com.hoaianh.smsfilter.ui.message;

import com.hoaianh.smsfilter.data.db.model.dao.Message;
import com.hoaianh.smsfilter.ui.base.BaseView;

import java.util.List;

/**
 * Created by Nhat on 3/4/18.
 */

public interface MessageBaseView extends BaseView {

    void onLoadMessagesSuccess(List<Message> messages);

}
