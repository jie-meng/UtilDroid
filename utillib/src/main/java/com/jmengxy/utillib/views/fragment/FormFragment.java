package com.jmengxy.utillib.views.fragment;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;

import com.jmengxy.utillib.R;
import com.jmengxy.utillib.functors.Action2;


/**
 * Created by jiemeng on 06/09/2017.
 */

public abstract class FormFragment extends Fragment {

    private Action2<Boolean, Boolean> onErrorListener;

    private Snackbar snackbar;

    @Override
    public void onDestroyView() {
        if (getSnackBar().isShown()) {
            getSnackBar().dismiss();
        }
        super.onDestroyView();
    }

    protected void setOnErrorListener(Action2<Boolean, Boolean> onErrorListener) {
        this.onErrorListener = onErrorListener;
    }

    protected ViewGroup getMainLayout() {
        return (ViewGroup) getView();
    }

    protected void showError(String message) {
        getSnackBar().setText(message != null ? message : "");
        if (!getSnackBar().isShown()) {
            getSnackBar().show();
        }

        if (onErrorListener != null) {
            onErrorListener.apply(true, false);
        }
    }

    protected void hideError() {
        if (getSnackBar().isShown()) {
            getSnackBar().dismiss();
        }

        if (onErrorListener != null) {
            onErrorListener.apply(false, false);
        }
    }

    protected Snackbar getSnackBar() {
        if (snackbar == null) {
            snackbar = Snackbar.make(getMainLayout(), "", Snackbar.LENGTH_INDEFINITE)
                    .setActionTextColor(ContextCompat.getColor(getContext(), R.color.error_red))
                    .setAction(getString(R.string.close), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (onErrorListener != null) {
                                onErrorListener.apply(false, true);
                            }
                        }
                    });
        }

        return snackbar;
    }
}
