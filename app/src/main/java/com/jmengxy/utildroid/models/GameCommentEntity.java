package com.jmengxy.utildroid.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jiemeng on 26/02/2018.
 */

public class GameCommentEntity implements Parcelable {
    @SerializedName("avatar")
    String avatar;

    @SerializedName("name")
    String name;

    @SerializedName("comment")
    String comment;

    public GameCommentEntity() {
    }

    protected GameCommentEntity(Parcel in) {
        avatar = in.readString();
        name = in.readString();
        comment = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(avatar);
        dest.writeString(name);
        dest.writeString(comment);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GameCommentEntity> CREATOR = new Creator<GameCommentEntity>() {
        @Override
        public GameCommentEntity createFromParcel(Parcel in) {
            return new GameCommentEntity(in);
        }

        @Override
        public GameCommentEntity[] newArray(int size) {
            return new GameCommentEntity[size];
        }
    };

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameCommentEntity that = (GameCommentEntity) o;

        if (avatar != null ? !avatar.equals(that.avatar) : that.avatar != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return comment != null ? comment.equals(that.comment) : that.comment == null;
    }

    @Override
    public int hashCode() {
        int result = avatar != null ? avatar.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }
}
