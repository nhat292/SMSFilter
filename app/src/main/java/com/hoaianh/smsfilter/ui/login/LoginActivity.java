package com.hoaianh.smsfilter.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hoaianh.smsfilter.R;
import com.hoaianh.smsfilter.ui.base.BaseActivity;
import com.hoaianh.smsfilter.ui.main.MainActivity;
import com.hoaianh.smsfilter.utils.KeyboardUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hoaianh.smsfilter.utils.ScreenUtils.getScreenHeight;

/**
 * Created by Nhat on 3/6/18.
 */

public class LoginActivity extends BaseActivity implements LoginBaseView {

    public static Intent getStartIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Inject
    LoginMvpPresenter<LoginBaseView> mPresenter;

    @BindView(R.id.llContent)
    LinearLayout llContent;
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.editId)
    TextInputEditText editId;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);

        setUp();
    }

    @Override
    protected void setUp() {
        ViewTreeObserver vto = llContent.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    llContent.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    llContent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                int screenHeight = getScreenHeight(LoginActivity.this);
                int contentHeight = llContent.getMeasuredHeight();
                int marginTop = (screenHeight - contentHeight) / 2;
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) txtTitle.getLayoutParams();
                params.setMargins(0, marginTop, 0, 0);
                txtTitle.setLayoutParams(params);
            }
        });

        editId.setOnEditorActionListener((textView, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                KeyboardUtils.hideSoftInput(LoginActivity.this);
                mPresenter.login(editId.getText().toString());
                btnSubmit.setEnabled(false);
                return true;
            }
            return false;
        });
    }

    @Override
    public void onLoginSuccess() {
        startActivity(MainActivity.getStartIntent(this));
        finish();
    }

    @Override
    public void onLoginError(int resId) {
        btnSubmit.setEnabled(true);
        showMessage(resId);
    }

    @OnClick(R.id.btnSubmit)
    public void onSubmitClick() {
        mPresenter.login(editId.getText().toString());
        KeyboardUtils.hideSoftInput(this);
    }
}
