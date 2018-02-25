package com.jmengxy.utildroid.utils.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jiemeng on 21/02/2018.
 */

public class BtDevice implements Parcelable {
    @SerializedName("address")
    String address;

    @SerializedName("name")
    String name;

    public BtDevice() {
    }

    public BtDevice(String address, String name) {
        this.address = address;
        this.name = name;
    }

    protected BtDevice(Parcel in) {
        address = in.readString();
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BtDevice> CREATOR = new Creator<BtDevice>() {
        @Override
        public BtDevice createFromParcel(Parcel in) {
            return new BtDevice(in);
        }

        @Override
        public BtDevice[] newArray(int size) {
            return new BtDevice[size];
        }
    };

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getAddress() + " " + getName();
    }
}
