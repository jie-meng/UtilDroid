package com.jmengxy.utildroid.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jiemeng on 25/01/2018.
 */

public class Monster implements Parcelable {
    @SerializedName("id")
    String id;

    @SerializedName("name")
    String name;

    @SerializedName("attack")
    String attack;

    @SerializedName("defense")
    String defense;

    public Monster() {
    }

    protected Monster(Parcel in) {
        name = in.readString();
        attack = in.readString();
        defense = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(attack);
        dest.writeString(defense);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Monster> CREATOR = new Creator<Monster>() {
        @Override
        public Monster createFromParcel(Parcel in) {
            return new Monster(in);
        }

        @Override
        public Monster[] newArray(int size) {
            return new Monster[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttack() {
        return attack;
    }

    public void setAttack(String attack) {
        this.attack = attack;
    }

    public String getDefense() {
        return defense;
    }

    public void setDefense(String defense) {
        this.defense = defense;
    }
}
