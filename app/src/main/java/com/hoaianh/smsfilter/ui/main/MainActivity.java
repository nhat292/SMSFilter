package com.hoaianh.smsfilter.ui.main;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.multi.SnackbarOnAnyDeniedMultiplePermissionsListener;
import com.karumi.dexter.listener.single.CompositePermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.karumi.dexter.listener.single.SnackbarOnDeniedPermissionListener;
import com.hoaianh.smsfilter.R;
import com.hoaianh.smsfilter.adapter.SMSAdapter;
import com.hoaianh.smsfilter.callback.DialogCallback;
import com.hoaianh.smsfilter.data.db.model.dao.Message;
import com.hoaianh.smsfilter.ui.base.BaseActivity;
import com.hoaianh.smsfilter.ui.dialog.AppDialog;
import com.hoaianh.smsfilter.ui.message.MessageActivity;
import com.hoaianh.smsfilter.utils.AppLogger;
import com.hoaianh.smsfilter.utils.CommonUtils;
import com.hoaianh.smsfilter.utils.permission.ErrorPermissionRequestListener;
import com.hoaianh.smsfilter.utils.permission.MultiPermissionListener;
import com.hoaianh.smsfilter.utils.permission.PermissionResultListener;
import com.hoaianh.smsfilter.utils.permission.SinglePermissionListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Nhat on 3/4/18.
 */

public class MainActivity extends BaseActivity implements PermissionResultListener, MainBaseView {

    public static Intent getStartIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Inject
    MainMvpPresenter<MainBaseView> mPresenter;

    @BindView(R.id.recyclerSMS)
    RecyclerView recyclerSMS;
    @BindView(R.id.txtLastSync)
    TextView txtLastSync;

    private PermissionListener mPermissionListener;
    private MultiplePermissionsListener mMultiplePermissionsListener;
    private List<Message> mMessages = new ArrayList<>();
    private SMSAdapter mSmsAdapter;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);

        setUp();

        createPermissionListener();

        checkSMSPermission();

        EventBus.getDefault().register(this);

        mPresenter.getLastSync();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mHandler.removeCallbacks(loadMessageDelayRunnable);
    }

    @Override
    protected void setUp() {
        mSmsAdapter = new SMSAdapter(mMessages, (o, position) -> {
            Message message = (Message) o;
            startActivity(MessageActivity.getStartIntent(MainActivity.this, message.getAddress()));
        });
        recyclerSMS.setAdapter(mSmsAdapter);
    }

    @Override
    public void onLoadMessagesSuccess(List<Message> messages) {
        mMessages.clear();
        mMessages.addAll(messages);
        mSmsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveMessageToFirebaseSuccess() {
        showSimpleDialog(
                getString(R.string.success_title),
                getString(R.string.message_save_to_server_success)
        );
    }

    @Override
    public void onGetLastSyncSuccess(long time) {
        if (time != 0) {
            txtLastSync.setVisibility(View.VISIBLE);
            txtLastSync.setText(String.format(getString(R.string.last_sync_format), CommonUtils.showDateTime(new Date(time))));
        } else {
            txtLastSync.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDeleteMessageSuccess() {
        showSimpleDialog(
                getString(R.string.success_title),
                getString(R.string.message_deleted_messages_success)
        );
    }

    @OnClick(R.id.btnSyncToServer)
    public void onSyncToServerClick() {
        mPresenter.saveMessagesToFirebase();
    }

    @OnClick(R.id.btnDownload)
    public void onDownloadClick() {
        checkWriteExternalPermissionPermission();
    }

    @OnClick(R.id.btnDelete)
    public void onDeleteClick() {
        showConfirmDialog(
                getString(R.string.delete_title),
                getString(R.string.message_ask_delete_data_confirm),
                new DialogCallback<AppDialog>() {
                    @Override
                    public void onNegative(AppDialog dialog) {
                        dialog.dismissDialog(AppDialog.TAG);
                    }

                    @Override
                    public void onPositive(AppDialog dialog) {
                        dialog.dismissDialog(AppDialog.TAG);
                        mPresenter.deleteAllMessages();
                    }
                }

        );
    }

    private void checkSMSPermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.RECEIVE_SMS,
                        /*Manifest.permission.READ_CONTACTS,*/
                        Manifest.permission.READ_SMS
                )
                .withListener(mMultiplePermissionsListener)
                .withErrorListener(new ErrorPermissionRequestListener(this))
                .check();
    }

    private void checkWriteExternalPermissionPermission() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(mPermissionListener)
                .withErrorListener(new ErrorPermissionRequestListener(this))
                .check();
    }


    private void createPermissionListener() {
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);
        PermissionListener feedbackViewPermissionListener = new SinglePermissionListener(this);
        mPermissionListener = new CompositePermissionListener(feedbackViewPermissionListener,
                SnackbarOnDeniedPermissionListener.Builder.with(viewGroup,
                        R.string.permission_rational)
                        .withOpenSettingsButton(R.string.setting)
                        .withCallback(new Snackbar.Callback() {
                            @Override
                            public void onShown(Snackbar snackbar) {
                                super.onShown(snackbar);
                            }

                            @Override
                            public void onDismissed(Snackbar snackbar, int event) {
                                super.onDismissed(snackbar, event);
                            }
                        })
                        .build());

        MultiPermissionListener multiPermissionListener = new MultiPermissionListener(this);
        mMultiplePermissionsListener =
                new CompositeMultiplePermissionsListener(multiPermissionListener,
                        SnackbarOnAnyDeniedMultiplePermissionsListener.Builder.with(viewGroup,
                                R.string.permission_rational)
                                .withOpenSettingsButton(R.string.setting)
                                .build());


    }

    @Override
    public void onPermissionError(String error) {
        onError(error);
    }

    @Override
    public void onPermissionGranted(String permissionName) {
        if (permissionName.equals(Manifest.permission.READ_SMS)) {
            //Load SMS from phone
            mPresenter.getMessages();
        } else if (permissionName.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            //Download file from server
            mPresenter.downloadMessagesFromFirebase();
        }
    }

    @Override
    public void onPermissionDenied(String permissionName, boolean isPermanentDenied) {

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onPermissionRationale(PermissionToken token) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String message) {
        AppLogger.i(getClass().getSimpleName(), message);
        mHandler.postDelayed(loadMessageDelayRunnable, 1500);
    }

    private Runnable loadMessageDelayRunnable = () -> {
        mPresenter.getMessages();
    };

}
