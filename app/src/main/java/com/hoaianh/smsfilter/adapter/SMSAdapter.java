package com.hoaianh.smsfilter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hoaianh.smsfilter.R;
import com.hoaianh.smsfilter.data.db.model.dao.Message;
import com.hoaianh.smsfilter.viewholder.SMSViewHolder;

import java.util.List;

/**
 * Created by Nhat on 12/15/17.
 */

public class SMSAdapter extends RecyclerView.Adapter<SMSViewHolder> {

    private List<Message> mMessages;
    private ListItemCallback mListener;

    public SMSAdapter(List<Message> messages, ListItemCallback listener) {
        mMessages = messages;
        mListener = listener;
    }

    @Override
    public SMSViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sms_item, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        return new SMSViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(SMSViewHolder holder, int position) {
        holder.bind(mMessages.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }
}
