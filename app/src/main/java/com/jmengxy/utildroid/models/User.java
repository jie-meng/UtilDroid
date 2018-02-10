package com.jmengxy.utildroid.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jiemeng on 03/02/2018.
 */

public class User implements Parcelable {

    @SerializedName("username")
    String username;

    @SerializedName("nickname")
    String nickname;

    @SerializedName("password")
    String password;

    @SerializedName("email")
    String email;

    @SerializedName("mobile_number")
    String mobileNumber;

    @SerializedName("access_token")
    String accessToken;

    public User() {
    }

    protected User(Parcel in) {
        username = in.readString();
        nickname = in.readString();
        password = in.readString();
        email = in.readString();
        mobileNumber = in.readString();
        accessToken = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(nickname);
        dest.writeString(password);
        dest.writeString(email);
        dest.writeString(mobileNumber);
        dest.writeString(accessToken);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
