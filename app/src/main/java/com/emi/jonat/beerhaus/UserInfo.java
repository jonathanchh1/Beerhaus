package com.emi.jonat.beerhaus;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jonat on 1/1/2018.
 */

public class UserInfo implements Parcelable {

        private String id;
        private String text;
        private String name;
        private String photoUrl;
        private String imageUrl;



        public UserInfo(String text, String name, String photoUrl, String imageUrl) {
            this.text = text;
            this.name = name;
            this.photoUrl = photoUrl;
            this.imageUrl = imageUrl;
        }

    protected UserInfo(Parcel in) {
        id = in.readString();
        text = in.readString();
        name = in.readString();
        photoUrl = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhotoUrl() {
            return photoUrl;
        }

        public void setPhotoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(text);
        dest.writeString(name);
        dest.writeString(photoUrl);
        dest.writeString(imageUrl);
    }
}

