package com.hoaianh.smsfilter.ui.message;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hoaianh.smsfilter.R;
import com.hoaianh.smsfilter.adapter.MessageAdapter;
import com.hoaianh.smsfilter.data.db.model.dao.Message;
import com.hoaianh.smsfilter.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nhat on 3/4/18.
 */

public class MessageActivity extends BaseActivity implements MessageBaseView {

    private static final String EXTRA_ADDRESS = "ADDRESS";

    public static Intent getStartIntent(Context context, String senderAddress) {
        Intent intent = new Intent(context, MessageActivity.class);
        intent.putExtra(EXTRA_ADDRESS, senderAddress);
        return intent;
    }

    @BindView(R.id.recyclerMessage)
    RecyclerView recyclerMessage;

    @Inject
    MessageMvpPresenter<MessageBaseView> mPresenter;

    private List<Message> mMessages = new ArrayList<>();
    private MessageAdapter mMessageAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);

        setUp();
    }

    @Override
    protected void setUp() {
        String senderAddress = getIntent().getStringExtra(EXTRA_ADDRESS);
        mPresenter.getMessages(senderAddress);

        mMessageAdapter = new MessageAdapter(mMessages, (o, position) -> {

        });
        recyclerMessage.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerMessage.setLayoutManager(layoutManager);
        recyclerMessage.setAdapter(mMessageAdapter);
    }

    @Override
    public void onLoadMessagesSuccess(List<Message> messages) {
        mMessages.addAll(messages);
        mMessageAdapter.notifyDataSetChanged();
        recyclerMessage.scrollToPosition(mMessages.size() - 1);
    }
}
