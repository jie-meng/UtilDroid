package com.jmengxy.utildroid.workflows.bluetooth;

import com.jmengxy.utildroid.utils.bluetooth.BtDevice;
import com.jmengxy.utillib.architecture.mvp.BasePresenter;
import com.jmengxy.utillib.architecture.mvp.BaseView;

/**
 * Created by jiemeng on 04/02/2018.
 */

public interface BluetoothDevicesContract {
    interface View extends BaseView<Presenter> {
        void showProgress(boolean showOrHide);

        void showError(int code, String message);

        void foundNewDevice(BtDevice btDevice);
    }

    interface Presenter extends BasePresenter {
        void scan();
    }
}
