package com.example.jonat.services.Models;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import static com.example.jonat.services.data.UFCContract.MediaEntry.COL_DESCR;
import static com.example.jonat.services.data.UFCContract.MediaEntry.COL_INTERNAL_URL;
import static com.example.jonat.services.data.UFCContract.MediaEntry.COL_LAST_MODIFIED;
import static com.example.jonat.services.data.UFCContract.MediaEntry.COL_MEDIA_ID;
import static com.example.jonat.services.data.UFCContract.MediaEntry.COL_MEDIA_TITLE;
import static com.example.jonat.services.data.UFCContract.MediaEntry.COL_MORE_LINK;
import static com.example.jonat.services.data.UFCContract.MediaEntry.COL_MORE_LINKURL;
import static com.example.jonat.services.data.UFCContract.MediaEntry.COL_PUBLISHED;
import static com.example.jonat.services.data.UFCContract.MediaEntry.COL_THUMBNAIL;
import static com.example.jonat.services.data.UFCContract.MediaEntry.COL_TYPE;
import static com.example.jonat.services.data.UFCContract.MediaEntry.COL_URL_NAME;
import static com.example.jonat.services.data.UFCContract.MediaEntry.COL__MEDIA_DATE;

/**
 * Created by jonat on 10/11/2017.
 */

public class Media implements Parcelable{

    public static final Creator<Media> CREATOR = new Creator<Media>() {
        @Override
        public Media createFromParcel(Parcel in) {
            return new Media(in);
        }

        @Override
        public Media[] newArray(int size) {
            return new Media[size];
        }
    };
    @SerializedName("id")
    private int id;
    @SerializedName("media_date")
    private String media_date;
    @SerializedName("type")
    private String type;
    @SerializedName("description")
    private String description;
    @SerializedName("more_link_text")
    private String more_link_text;
    @SerializedName("thumbnail")
    private String thumbnail;
    @SerializedName("internal_url")
    private String internal_url;
    @SerializedName("title")
    private String title;
    @SerializedName("more_linkurl")
    private String more_linkurl;
    @SerializedName("embedded_type")
    private String embedded_type;
    @SerializedName("embedded_id")
    private String embedded_id;
    @SerializedName("mobile_stream_url")
    private String mobile_stream_url;
    @SerializedName("mobile_video_url")
    private String mobile_video;
    @SerializedName("last_modified")
    private String last_modified;
    @SerializedName("url_name")
    private String url_name;
    @SerializedName("created")
    private String created;
    @SerializedName("keyword_ids")
    private List keyword_ids;
    @SerializedName("published_start_date")
    private String published_start_date;

    public Media(Cursor cursor) {
        this.id = cursor.getInt(COL_MEDIA_ID);
        this.media_date = cursor.getString(COL__MEDIA_DATE);
        this.type = cursor.getString(COL_TYPE);
        this.description = cursor.getString(COL_DESCR);
        this.more_link_text = cursor.getString(COL_MORE_LINK);
        this.thumbnail = cursor.getString(COL_THUMBNAIL);
        this.internal_url = cursor.getString(COL_INTERNAL_URL);
        this.title = cursor.getString(COL_MEDIA_TITLE);
        this.more_linkurl = cursor.getString(COL_MORE_LINKURL);
        this.last_modified = cursor.getString(COL_LAST_MODIFIED);
        this.url_name = cursor.getString(COL_URL_NAME);
        this.published_start_date = cursor.getString(COL_PUBLISHED);

    }

    public Media() {

    }

    public Media(Parcel in) {
        id = in.readInt();
        media_date = in.readString();
        type = in.readString();
        description = in.readString();
        more_link_text = in.readString();
        thumbnail = in.readString();
        internal_url = in.readString();
        title = in.readString();
        more_linkurl = in.readString();
        embedded_type = in.readString();
        embedded_id = in.readString();
        mobile_stream_url = in.readString();
        mobile_video = in.readString();
        last_modified = in.readString();
        url_name = in.readString();
        created = in.readString();
        published_start_date = in.readString();
    }

    public String getMore_linkurl() {
        return more_linkurl;
    }

    public void setMore_linkurl(String more_linkurl) {
        this.more_linkurl = more_linkurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getMedia_date() {
        return media_date;
    }

    public void setMedia_date(String media_date) {
        this.media_date = media_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmbedded_id() {
        return embedded_id;
    }

    public void setEmbedded_id(String embedded_id) {
        this.embedded_id = embedded_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInternal_url() {
        return internal_url;
    }

    public void setInternal_url(String internal_url) {
        this.internal_url = internal_url;
    }

    public String getMore_link_text() {
        return more_link_text;
    }

    public void setMore_link_text(String more_link_text) {
        this.more_link_text = more_link_text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List getKeyword_ids() {
        return keyword_ids;
    }

    public void setKeyword_ids(List keyword_ids) {
        this.keyword_ids = keyword_ids;
    }

    public String getLast_modified() {
        return last_modified;
    }

    public void setLast_modified(String last_modified) {
        this.last_modified = last_modified;
    }

    public String getMobile_stream_url() {
        return mobile_stream_url;
    }

    public void setMobile_stream_url(String mobile_stream_url) {
        this.mobile_stream_url = mobile_stream_url;
    }

    public String getMobile_video() {
        return mobile_video;
    }

    public void setMobile_video(String mobile_video) {
        this.mobile_video = mobile_video;
    }

    public String getPublished_start_date() {
        return published_start_date;
    }

    public void setPublished_start_date(String published_start_date) {
        this.published_start_date = published_start_date;
    }

    public String getUrl_name() {
        return url_name;
    }

    public void setUrl_name(String url_name) {
        this.url_name = url_name;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getEmbedded_type() {
        return embedded_type;
    }

    public void setEmbedded_type(String embedded_type) {
        this.embedded_type = embedded_type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(media_date);
        parcel.writeString(type);
        parcel.writeString(description);
        parcel.writeString(more_link_text);
        parcel.writeString(thumbnail);
        parcel.writeString(internal_url);
        parcel.writeString(title);
        parcel.writeString(more_linkurl);
        parcel.writeString(embedded_type);
        parcel.writeString(embedded_id);
        parcel.writeString(mobile_stream_url);
        parcel.writeString(mobile_video);
        parcel.writeString(last_modified);
        parcel.writeString(url_name);
        parcel.writeString(created);
        parcel.writeString(published_start_date);
    }
}
