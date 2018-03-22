
package com.hoaianh.smsfilter.data.db;

import com.hoaianh.smsfilter.data.db.model.dao.Message;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Nhat on 12/13/17.
 */


public interface DbHelper {

    Observable<Boolean> saveMessages(List<Message> messages);

    Observable<List<Message>> getMessagesUniqueSender();

    Observable<List<Message>> getMessages(String senderAddress);

    Observable<List<Message>> getMessages();

    Observable<Boolean> saveMessage(Message message);

    void updateMessages(List<Message> messages);

}
