package com.example.jonat.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by jonat on 10/16/2017.
 */

public class UFCContract {
    //it should be unique in system,we use package name because it is unique
    public static final String CONTENT_AUTHORITY = "com.example.jonat.services.data";

    //base URI for content provider
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    //URI end points for Content provider
    public static final String PATH_EVENT = "ufc";

    public static final String PATH_MEDIA = "media";

    //for favorites
    public static final class UFCEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_EVENT).build();


        //these are MIME types ,not really but they are similar to MIME types
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_EVENT;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_EVENT;


        // Table name
        public static final String TABLE_NAME = "events";
        public static final String COLUMN_EVENT_ID = "id";
        public static final String COLUMN_EVENT_DATE = "event_date";
        public static final String COLUMN_FEATURE_IMAGE = "feature_image";
        public static final String COLUMN_SECONDARY_IMAGE = "secondary_feature_image";
        public static final String COLUMN_TICKET_IMAGE = "ticket_image";
        public static final String COLUMN_EVENTS_TIME = "event_time_zone_text";
        public static final String COLUMN_DESCRIPTION = "short_description";
        public static final String COLUMN_END_EVENT = "end_event_dategmt";
        public static final String COLUMN_TICKET_URL = "ticketurl";
        public static final String COLUMN_TICKET_SELLER = "ticket_seller_name";
        public static final String COLUMN_TITLE = "base_title";
        public static final String COLUMN_TITLE_TAG = "title_tag_line";
        public static final String COLUMN_TICKET = "ticket_general_sale_date";
        public static final String COLUMN_SUBTITLE = "subtitle";
        public static final String COLUMN_EVENTS_STATUS = "event_status";
        public static final String COLUMN_CORNER_AUDIO = "corner_audio_available";
        public static final String COLUMN_ARENA = "arena";
        public static final String COLUMN_LOCATION = "location";

        public static final int COL_ID = 0;
        public static final int COL_EVENT_ID = 1;
        public static final int COL__EVENT_DATE = 2;
        public static final int COL_SECONDARY_IMAGE = 3;
        public static final int COL_FEATURE_IMAGE = 4;
        public static final int COL_TICKET_IMAGE = 5;
        public static final int COL_EVENTS_TIME = 6;
        public static final int COL_DESCRIPTION = 7;
        public static final int COL_END_EVENT = 8;
        public static final int COL_TICKET_URL = 9;
        public static final int COL_TICKET_SELLER = 10;
        public static final int COL_TITLE = 11;
        public static final int COL_TITLE_TAG = 12;
        public static final int COL_TICKET = 13;
        public static final int COL_SUBTITLE = 14;
        public static final int COL_EVENTS_STATUS = 15;
        public static final int COL_CORNER_AUDIO = 16;
        public static final int COL_ARENA = 17;
        public static final int COL_LOCATION = 18;

        public static Uri buildEventUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class MediaEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MEDIA).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MEDIA;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MEDIA;

        //media table
        public static final String TABLE_NAME = "media";
        public static final String COLUMN_MEDIA_ID = "id";
        public static final String COLUMN_MEDIA_DATE = "media_date";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_DESCR = "description";
        public static final String COLUMN_MORE_LINK = "more_link_text";
        public static final String COLUMN_THUMBNAIL = "thumbnail";
        public static final String COLUMN_INTERNAL_URL = "internal_url";
        public static final String COLUMN_MEDIA_TITLE = "title";
        public static final String COLUMN_MORE_LINKURL = "more_linkurl";
        public static final String COLUMN_LAST_MODIFIED = "last_modified";
        public static final String COLUMN_URL_NAME = "url_name";
        public static final String COLUMN_PUBLISHED = "published_start_date";
        public static final int COL_M_ID = 0;
        public static final int COL_MEDIA_ID = 1;
        public static final int COL__MEDIA_DATE = 2;
        public static final int COL_TYPE = 3;
        public static final int COL_DESCR = 4;
        public static final int COL_MORE_LINK = 5;
        public static final int COL_THUMBNAIL = 6;
        public static final int COL_INTERNAL_URL = 7;
        public static final int COL_MEDIA_TITLE = 8;
        public static final int COL_MORE_LINKURL = 9;
        public static final int COL_LAST_MODIFIED = 10;
        public static final int COL_URL_NAME = 11;
        public static final int COL_PUBLISHED = 12;

        public static Uri buildMediaUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

}
