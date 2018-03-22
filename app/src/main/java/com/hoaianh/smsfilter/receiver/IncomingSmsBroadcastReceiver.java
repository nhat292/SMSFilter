package com.hoaianh.smsfilter.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.hoaianh.smsfilter.App;
import com.hoaianh.smsfilter.dagger.component.BroadcastReceiverComponent;
import com.hoaianh.smsfilter.dagger.component.DaggerBroadcastReceiverComponent;
import com.hoaianh.smsfilter.data.DataManager;
import com.hoaianh.smsfilter.data.db.model.dao.Message;
import com.hoaianh.smsfilter.utils.AppLogger;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

public class IncomingSmsBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "IncomingSmsBroadcastReceiver";

    @Inject
    DataManager mDataManager;

    @Override
    public void onReceive(Context context, Intent intent) {

        BroadcastReceiverComponent component = DaggerBroadcastReceiverComponent.builder()
                .applicationComponent((App.getInstance()).getComponent())
                .build();
        component.inject(this);

        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            return;
        }
        Object[] pdusObj = (Object[]) bundle.get("pdus");
        if (pdusObj == null || pdusObj.length <= 0) return;
        for (int i = 0; i < pdusObj.length; i++) {
            SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
            String address = currentMessage.getOriginatingAddress();
            String body = currentMessage.getDisplayMessageBody();
            String type = Message.TYPE_RECEIVE;
            String date = String.valueOf(currentMessage.getTimestampMillis());

            Message message = new Message();
            message.setAddress(address);
            message.setBody(body);
            message.setType(type);
            message.setDate(date);
            message.setIsSync(false);

            mDataManager.saveMessage(message);
            AppLogger.i(TAG, "Message saved");

            EventBus.getDefault().post("You have a new message");
        }
    }
}
