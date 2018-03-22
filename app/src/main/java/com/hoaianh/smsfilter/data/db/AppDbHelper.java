
package com.hoaianh.smsfilter.data.db;


import com.hoaianh.smsfilter.data.db.model.dao.DaoMaster;
import com.hoaianh.smsfilter.data.db.model.dao.DaoSession;
import com.hoaianh.smsfilter.data.db.model.dao.Message;
import com.hoaianh.smsfilter.data.db.model.dao.MessageDao;

import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;


/**
 * Created by Nhat on 12/13/17.
 */


@Singleton
public class AppDbHelper implements DbHelper {

    private final DaoSession mDaoSession;

    @Inject
    public AppDbHelper(DbOpenHelper dbOpenHelper) {
        mDaoSession = new DaoMaster(dbOpenHelper.getWritableDb()).newSession();
    }

    @Override
    public Observable<Boolean> saveMessages(List<Message> messages) {
        return Observable.fromCallable(() -> {
            mDaoSession.getMessageDao().insertOrReplaceInTx(messages);
            return true;
        });
    }

    @Override
    public Observable<List<Message>> getMessagesUniqueSender() {
        return Observable.fromCallable(() -> mDaoSession.getMessageDao()
                .queryBuilder().where(new WhereCondition.StringCondition("1 GROUP BY address"))
                .orderDesc(MessageDao.Properties.Date).list());
    }

    @Override
    public Observable<List<Message>> getMessages(String senderAddress) {
        return Observable.fromCallable(() -> mDaoSession.getMessageDao().
                queryBuilder().where(MessageDao.Properties.Address.eq(senderAddress))
                .orderAsc(MessageDao.Properties.Date).list());
    }

    @Override
    public Observable<List<Message>> getMessages() {
        return Observable.fromCallable(() -> mDaoSession.getMessageDao().
                queryBuilder().orderDesc(MessageDao.Properties.Date).list());
    }

    @Override
    public Observable<Boolean> saveMessage(Message message) {
        return Observable.fromCallable(() -> {
            mDaoSession.getMessageDao().insertOrReplaceInTx(message);
            return true;
        });
    }

    @Override
    public void updateMessages(List<Message> messages) {
        mDaoSession.getMessageDao().updateInTx(messages);
    }
}
