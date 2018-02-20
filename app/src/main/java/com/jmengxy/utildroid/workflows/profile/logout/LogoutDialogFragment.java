package com.jmengxy.utildroid.workflows.profile.logout;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.jmengxy.utildroid.R;
import com.jmengxy.utildroid.app.AppComponent;
import com.jmengxy.utildroid.app.UtilApplication;
import com.jmengxy.utildroid.workflows.home.MainActivity;

import timber.log.Timber;

public class LogoutDialogFragment extends DialogFragment {
    public static final String TAG = "LogoutDialogFragment";

    public static LogoutDialogFragment newInstance() {
        return new LogoutDialogFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @android.support.annotation.NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AppComponent appComponent = UtilApplication.get(getContext()).getAppComponent();

        return new AlertDialog.Builder(getContext())
                .setMessage(R.string.logout_message)
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    appComponent.getDataSource().logout().subscribeOn(appComponent.getSchedulerProvider().io()).subscribe((o, throwable) -> Timber.d(throwable, "Logout result: ", o));
                    appComponent.getAccountHoster().deleteAccount();
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.putExtra(MainActivity.ARG_SHOW_LOGIN, true);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                })
                .setNegativeButton(R.string.cancel, null)
                .create();
    }
}
