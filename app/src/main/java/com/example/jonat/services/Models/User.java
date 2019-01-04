package com.example.jonat.services.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jonat on 1/2/2017.
 */

public class User implements Parcelable {

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

    public String username;
    public String email;
    public String UserProfile;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    protected User(Parcel in) {
        username = in.readString();
        email = in.readString();
        UserProfile = in.readString();
    }

    public String getUserProfile() {
        return UserProfile;
    }

    public void setUserProfile(String userProfile) {
        UserProfile = userProfile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(email);
        dest.writeString(UserProfile);
    }
}