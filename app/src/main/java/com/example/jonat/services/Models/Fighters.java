package com.example.jonat.services.Models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class Fighters implements Parcelable {
  @SerializedName("id")
    private int id;
  @SerializedName("wins")
    private int wins;
  @SerializedName("losses")
    private int losses;
  @SerializedName("last_name")
    private String last_name;
  @SerializedName("weight_class")
    private String weight_class;
  @SerializedName("title_holder")
    private boolean title_holder;
  @SerializedName("draw")
    private int draws;
  @SerializedName("first_name")
    private String first_name;
  @SerializedName("fighter_status")
  private String fighter_status;
  @SerializedName("thumbnail")
    private String thumbnail;


    public void setId(int id) {
        this.id = id;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public void setTitle_holder(boolean title_holder) {
        this.title_holder = title_holder;
    }

    public void setWeight_class(String weight_class) {
        this.weight_class = weight_class;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public void setFighter_status(String fighter_status) {
        this.fighter_status = fighter_status;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getId() {
        return id;
    }

    public int getDraws() {
        return draws;
    }

    public int getLosses() {
        return losses;
    }

    public int getWins() {
        return wins;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getFighter_status() {
        return fighter_status;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getWeight_class() {
        return weight_class;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    protected Fighters(Parcel in) {
        id = in.readInt();
        wins = in.readInt();
        losses = in.readInt();
        last_name = in.readString();
        weight_class = in.readString();
        title_holder = in.readByte() != 0;
        draws = in.readInt();
        first_name = in.readString();
        fighter_status = in.readString();
        thumbnail = in.readString();
    }

    public static final Creator<Fighters> CREATOR = new Creator<Fighters>() {
        @Override
        public Fighters createFromParcel(Parcel in) {
            return new Fighters(in);
        }

        @Override
        public Fighters[] newArray(int size) {
            return new Fighters[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(wins);
        parcel.writeInt(losses);
        parcel.writeString(last_name);
        parcel.writeString(weight_class);
        parcel.writeByte((byte) (title_holder ? 1 : 0));
        parcel.writeInt(draws);
        parcel.writeString(first_name);
        parcel.writeString(fighter_status);
        parcel.writeString(thumbnail);
    }
}
