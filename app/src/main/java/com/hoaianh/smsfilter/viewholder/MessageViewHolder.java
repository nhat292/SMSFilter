package com.hoaianh.smsfilter.viewholder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hoaianh.smsfilter.R;
import com.hoaianh.smsfilter.adapter.ListItemCallback;
import com.hoaianh.smsfilter.data.db.model.dao.Message;
import com.hoaianh.smsfilter.ui.base.BaseViewHolder;
import com.hoaianh.smsfilter.utils.CommonUtils;

import java.util.Date;

import butterknife.BindView;

/**
 * Created by Nhat on 1/9/18.
 */

public class MessageViewHolder extends BaseViewHolder {

    @BindView(R.id.llLeft)
    LinearLayout llLeft;
    @BindView(R.id.txtNameLeft)
    TextView txtNameLeft;
    @BindView(R.id.txtMessageLeft)
    TextView txtMessageLeft;
    @BindView(R.id.txtTimeLeft)
    TextView txtTimeLeft;

    @BindView(R.id.llRight)
    LinearLayout llRight;
    @BindView(R.id.txtNameRight)
    TextView txtNameRight;
    @BindView(R.id.txtMessageRight)
    TextView txtMessageRight;
    @BindView(R.id.txtTimeRight)
    TextView txtTimeRight;


    public MessageViewHolder(View itemView, ListItemCallback listItemCallback) {
        super(itemView, listItemCallback);
    }

    @Override
    protected void clear() {

    }

    @Override
    public void bind(Object o, int position) {
        Message message = (Message) o;
        if (message.getType().equals(Message.TYPE_RECEIVE)) {
            llLeft.setVisibility(View.VISIBLE);
            llRight.setVisibility(View.GONE);

            txtNameLeft.setText(message.getAddress());
            txtMessageLeft.setText(message.getBody());
            long time = Long.parseLong(message.getDate());
            if (time != 0) {
                txtTimeLeft.setText(CommonUtils.showDateTime(new Date(time)));
            } else {
                txtTimeLeft.setText("Unknown");
            }
            llLeft.setOnLongClickListener(view -> {
                copyMessage(message.getBody());
                return false;
            });
        } else {
            llLeft.setVisibility(View.GONE);
            llRight.setVisibility(View.VISIBLE);

            txtNameRight.setText("Me");
            txtMessageRight.setText(message.getBody());
            long time = Long.parseLong(message.getDate());
            if (time != 0) {
                txtTimeRight.setText(CommonUtils.showDateTime(new Date(time)));
            } else {
                txtTimeRight.setText("Unknown");
            }
            llRight.setOnLongClickListener(view -> {
                copyMessage(message.getBody());
                return false;
            });
        }
    }

    private void copyMessage(String message) {
        CommonUtils.setClipboard(itemView.getContext(), message);
        Toast.makeText(itemView.getContext(), itemView.getContext().getString(R.string.message_copied), Toast.LENGTH_LONG).show();
    }
}
