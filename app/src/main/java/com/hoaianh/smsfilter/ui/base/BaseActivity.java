
package com.hoaianh.smsfilter.ui.base;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.hoaianh.smsfilter.App;
import com.hoaianh.smsfilter.R;
import com.hoaianh.smsfilter.callback.DialogCallback;
import com.hoaianh.smsfilter.dagger.component.ActivityComponent;
import com.hoaianh.smsfilter.dagger.component.DaggerActivityComponent;
import com.hoaianh.smsfilter.dagger.module.ActivityModule;
import com.hoaianh.smsfilter.ui.dialog.AppDialog;
import com.hoaianh.smsfilter.utils.AppUiUtils;
import com.hoaianh.smsfilter.utils.NetworkUtils;

import butterknife.Unbinder;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Nhat on 12/13/17.
 */


public abstract class BaseActivity extends AppCompatActivity
        implements BaseView, BaseFragment.Callback {

    private ProgressDialog mProgressDialog;

    private ActivityComponent mActivityComponent;

    private Unbinder mUnBinder;

    private App mApp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp = (App) getApplicationContext();
        mApp.onActivityCreated(this, savedInstanceState);
        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(((App) getApplication()).getComponent())
                .build();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mApp.onActivityStarted(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mApp.onActivityResumed(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mApp.onActivityPaused(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mApp.onActivityStopped(this);
    }

    @Override
    protected void onDestroy() {
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        super.onDestroy();
        mApp.onActivityDestroyed(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mApp.onActivitySaveInstanceState(this, outState);
    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void showLoading() {
        hideLoading();
        mProgressDialog = AppUiUtils.showLoadingDialog(this);
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    private void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        TextView textView = sbView
                .findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.white));
        snackbar.show();
    }

    @Override
    public void onError(String message) {
        if (message != null) {
            showSnackBar(message);
        } else {
            showSnackBar(getString(R.string.some_error));
        }
    }

    @Override
    public void onError(@StringRes int resId) {
        onError(getString(resId));
    }

    @Override
    public void showMessage(String message) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.some_error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showMessage(@StringRes int resId) {
        showMessage(getString(resId));
    }


    @Override
    public void showSimpleDialog(String title, String message) {
        AppDialog dialog = AppDialog.newInstance();
        dialog.show(getSupportFragmentManager(), title, message, false);
        dialog.setCallback(new DialogCallback<AppDialog>() {
            @Override
            public void onNegative(AppDialog dialog) {
                dialog.dismissDialog(AppDialog.TAG);
            }

            @Override
            public void onPositive(AppDialog dialog) {
                dialog.dismissDialog(AppDialog.TAG);
            }
        });
    }

    @Override
    public void showSimpleDialogWithConfirm(String title, String message, DialogCallback callback) {
        AppDialog dialog = AppDialog.newInstance();
        dialog.show(getSupportFragmentManager(), title, message, false);
        dialog.setCallback(callback);
    }

    @Override
    public void showConfirmDialog(String title, String message, DialogCallback callback) {
        AppDialog dialog = AppDialog.newInstance();
        dialog.show(getSupportFragmentManager(), title, message, true);
        dialog.setCallback(callback);
    }

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void openActivityOnTokenExpire() {

    }

    public void setUnBinder(Unbinder unBinder) {
        mUnBinder = unBinder;
    }

    protected abstract void setUp();
}
