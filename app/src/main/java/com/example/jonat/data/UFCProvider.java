package com.example.jonat.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.jonat.services.Fragments.MediasFragment;


/**
 * Created by jonat on 10/16/2017.
 */

public class UFCProvider extends ContentProvider {

    static final int EVENTS = 100;
    static final int MEDIAS = 200;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private UFCDbHelper mOpenHelper;

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = UFCContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding codes.
        matcher.addURI(authority, UFCContract.PATH_EVENT, EVENTS);
        matcher.addURI(authority, UFCContract.PATH_MEDIA, MEDIAS);


        return matcher;
    }

    @Override
    public boolean onCreate() {
        //don't do long running task here , just initialize db but don't create tables and other stuff
        mOpenHelper = new UFCDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case EVENTS: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        UFCContract.UFCEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            }

            case MEDIAS: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        UFCContract.MediaEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        //noinspection ConstantConditions
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case EVENTS:
                return UFCContract.UFCEntry.CONTENT_TYPE;
            case MEDIAS:
                return UFCContract.MediaEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Uri returnUri;

        switch (sUriMatcher.match(uri)) {
            case EVENTS: {
                long _id = db.insert(UFCContract.UFCEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = UFCContract.UFCEntry.buildEventUri(_id);
                    Log.d(MediasFragment.TAG, "inserted: " + returnUri.toString());
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case MEDIAS: {
                long _id = db.insert(UFCContract.MediaEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = UFCContract.MediaEntry.buildMediaUri(_id);
                    Log.d(MediasFragment.TAG, "inserted: " + returnUri.toString());
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        switch (sUriMatcher.match(uri)) {
            case EVENTS:
                rowsDeleted = db.delete(
                        UFCContract.UFCEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case MEDIAS:
                rowsDeleted = db.delete(
                        UFCContract.MediaEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (selection == null || rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rowsUpdated;

        switch (sUriMatcher.match(uri)) {
            case EVENTS:
                rowsUpdated = db.update(UFCContract.UFCEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                Log.d(MediasFragment.TAG, String.valueOf(rowsUpdated));
                break;

            case MEDIAS:
                rowsUpdated = db.update(UFCContract.MediaEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                Log.d(MediasFragment.TAG, String.valueOf(rowsUpdated));

                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }
}
