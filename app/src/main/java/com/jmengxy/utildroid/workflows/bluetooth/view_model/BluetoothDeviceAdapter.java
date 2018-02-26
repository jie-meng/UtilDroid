package com.jmengxy.utildroid.workflows.bluetooth.view_model;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jmengxy.utildroid.R;
import com.jmengxy.utildroid.app.UtilApplication;
import com.jmengxy.utildroid.utils.bluetooth.BtDevice;
import com.jmengxy.utillib.adapters.DiffCallback;
import com.jmengxy.utillib.functors.Action1;
import com.jmengxy.utillib.schedulers.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Single;

/**
 * Created by jiemeng on 21/02/2018.
 */

public class BluetoothDeviceAdapter extends RecyclerView.Adapter<BluetoothDeviceAdapter.BluetoothDeviceViewHolder> {

    private final Context context;

    private final SchedulerProvider schedulerProvider;

    private Action1<BtDevice> itemClickListener;

    private List<BtDevice> btDeviceList;

    public BluetoothDeviceAdapter(Context context) {
        this.context = context;
        schedulerProvider = UtilApplication.get(context).getAppComponent().getSchedulerProvider();
        btDeviceList = new ArrayList<>();
    }

    public void setData(List<BtDevice> btDevices) {
        Single.fromCallable(() -> DiffUtil.calculateDiff(new DiffCallback<>(this.btDeviceList, btDevices), true))
                .subscribeOn(schedulerProvider.computation())
                .observeOn(schedulerProvider.ui())
                .subscribe((diffResult, throwable) -> {
                    this.btDeviceList.clear();
                    this.btDeviceList = btDevices;
                    diffResult.dispatchUpdatesTo(BluetoothDeviceAdapter.this);
                });
    }

    public void addData(BtDevice btDevice) {
        btDeviceList.add(btDevice);
        notifyItemInserted(btDeviceList.size() - 1);
    }

    @Override
    public BluetoothDeviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BluetoothDeviceViewHolder(LayoutInflater.from(context), parent, itemClickListener);
    }

    @Override
    public void onBindViewHolder(BluetoothDeviceViewHolder holder, int position) {
        holder.bind(btDeviceList.get(position));
    }

    public void setItemClickListener(Action1<BtDevice> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        return btDeviceList.size();
    }


    public static class BluetoothDeviceViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView tvName;

        @BindView(R.id.address)
        TextView tvAddress;

        public BluetoothDeviceViewHolder(LayoutInflater inflater, ViewGroup parent, Action1<BtDevice> itemClickListener) {
            super(inflater.inflate(R.layout.bluetooth_device_item_view, parent, false));
            ButterKnife.bind(this, itemView);
        }

        public void bind(BtDevice btDevice) {
            tvName.setText(btDevice.getName());
            tvAddress.setText(btDevice.getAddress());
        }
    }
}
