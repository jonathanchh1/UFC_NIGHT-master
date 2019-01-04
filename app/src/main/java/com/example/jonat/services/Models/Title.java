package com.example.jonat.services.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jonat on 10/11/2017.
 */

public class Title implements Parcelable{

    public static final Creator<Title> CREATOR = new Creator<Title>() {
        @Override
        public Title createFromParcel(Parcel in) {
            return new Title(in);
        }

        @Override
        public Title[] newArray(int size) {
            return new Title[size];
        }
    };
    @SerializedName("id")
    private int id;
    @SerializedName("wins")
    private String wins;
    @SerializedName("statid")
    private String losses;
    @SerializedName("last_name")
    private String last_name;
    @SerializedName("weight_class")
    private String weightclass;
    @SerializedName("title_holder")
    private String titleholders;
    @SerializedName("draws")
    private String draws;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("fighter_status")
    private String fighterActive;
    @SerializedName("thumbnail")
    private String thumbnail;

    protected Title(Parcel in) {
        id = in.readInt();
        wins = in.readString();
        losses = in.readString();
        last_name = in.readString();
        weightclass = in.readString();
        titleholders = in.readString();
        draws = in.readString();
        firstName = in.readString();
        fighterActive = in.readString();
        thumbnail = in.readString();
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDraws() {
        return draws;
    }

    public void setDraws(String draws) {
        this.draws = draws;
    }

    public String getFighterActive() {
        return fighterActive;
    }

    public void setFighterActive(String fighterActive) {
        this.fighterActive = fighterActive;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getTitleholders() {
        return titleholders;
    }

    public void setTitleholders(String titleholders) {
        this.titleholders = titleholders;
    }

    public String getLosses() {
        return losses;
    }

    public void setLosses(String losses) {
        this.losses = losses;
    }

    public String getWeightclass() {
        return weightclass;
    }

    public void setWeightclass(String weightclass) {
        this.weightclass = weightclass;
    }

    public String getWins() {
        return wins;
    }

    public void setWins(String wins) {
        this.wins = wins;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(wins);
        dest.writeString(losses);
        dest.writeString(last_name);
        dest.writeString(weightclass);
        dest.writeString(titleholders);
        dest.writeString(draws);
        dest.writeString(firstName);
        dest.writeString(fighterActive);
        dest.writeString(thumbnail);
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
