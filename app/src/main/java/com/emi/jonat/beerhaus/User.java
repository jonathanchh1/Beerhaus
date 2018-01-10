package com.emi.jonat.beerhaus;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jonat on 1/4/2018.
 */

public class User implements Parcelable {

    public String username;
    public String email;
    public String mphoto;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }


    protected User(Parcel in) {
        username = in.readString();
        email = in.readString();
        mphoto = in.readString();
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

    public User(String name, String email, String photo) {
        this.username = name;
        this.email = email;
        this.mphoto = photo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMphoto(String mphoto) {
        this.mphoto = mphoto;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public String getMphoto() {
        return mphoto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(email);
        dest.writeString(mphoto);
    }
}
