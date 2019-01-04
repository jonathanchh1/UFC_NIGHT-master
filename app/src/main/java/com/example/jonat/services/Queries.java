package com.example.jonat.services;

import android.content.Context;
import android.database.Cursor;

import com.example.jonat.services.data.UFCContract;

/**
 * Created by jonat on 10/23/2017.
 */

public class Queries {
    //takes id and tells whether favorite is added or not
    public static int isFavorited(Context context, int id) {
        Cursor cursor = context.getContentResolver().query(
                UFCContract.UFCEntry.CONTENT_URI,
                null,   // projection
                UFCContract.UFCEntry.COLUMN_EVENT_ID + " = ?", // selection
                new String[]{Integer.toString(id)},   // selectionArgs
                null    // sort order
        );
        int numRows = cursor.getCount();
        cursor.close();
        return numRows;
    }

    public static int isFavoritedMedia(Context context, int id) {
        Cursor cursor = context.getContentResolver().query(
                UFCContract.MediaEntry.CONTENT_URI,
                null,   // projection
                UFCContract.MediaEntry.COLUMN_MEDIA_ID + " = ?", // selection
                new String[]{Integer.toString(id)},   // selectionArgs
                null    // sort order
        );
        int numRows = cursor.getCount();
        cursor.close();
        return numRows;
    }
}
