package com.hoaianh.smsfilter.viewholder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hoaianh.smsfilter.R;
import com.hoaianh.smsfilter.adapter.ListItemCallback;
import com.hoaianh.smsfilter.data.db.model.dao.Message;
import com.hoaianh.smsfilter.ui.base.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by Nhat on 3/4/18.
 */

public class SMSViewHolder extends BaseViewHolder {

    @BindView(R.id.llItem)
    LinearLayout llItem;
    @BindView(R.id.txtAddress)
    TextView txtAddress;

    public SMSViewHolder(View itemView, ListItemCallback listItemCallback) {
        super(itemView, listItemCallback);
    }

    @Override
    protected void clear() {

    }

    @Override
    public void bind(Object o, int position) {
        if (o instanceof Message) {
            Message message = (Message) o;
            txtAddress.setText(message.getAddress());
        }
        llItem.setOnClickListener(view -> {
            if(getListItemCallback() != null) {
                getListItemCallback().onClick(o, position);
            }
        });
    }
}
