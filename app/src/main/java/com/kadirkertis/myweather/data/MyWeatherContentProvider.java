package com.kadirkertis.myweather.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.kadirkertis.myweather.data.MyWeatherDbContract.CONTENT_AUTHORITY;
import static com.kadirkertis.myweather.data.MyWeatherDbContract.PATH_WEATHER_INFO;
import static com.kadirkertis.myweather.data.MyWeatherDbContract.WeatherInfoTable;

/**
 * Created by Kadir Kertis on 17.7.2017.
 */

public class MyWeatherContentProvider extends ContentProvider {

    private static final String TAG = MyWeatherContentProvider.class.getSimpleName();
    private static final String UNKNOWN_URI = "Unknown uri: ";
    public static final int WEATHERS = 100;
    public static final int WEATHER_FOR_ID = 101;

    private MyWeatherDbHelper myWeatherDbHelper;
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(CONTENT_AUTHORITY, PATH_WEATHER_INFO, WEATHERS);
        matcher.addURI(CONTENT_AUTHORITY, PATH_WEATHER_INFO +"/#", WEATHER_FOR_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        myWeatherDbHelper = new MyWeatherDbHelper(getContext());
        return true;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case WEATHERS:
                return WeatherInfoTable.CONTENT_DIR_TYPE;
            case WEATHER_FOR_ID:
                return WeatherInfoTable.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException(UNKNOWN_URI + uri);
        }
    }


    @Override
    public Cursor query(@NonNull Uri uri,
                        @Nullable String[] projection,
                        @Nullable String selection,
                        @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        Cursor responseCursor;
        final SQLiteDatabase db = myWeatherDbHelper.getReadableDatabase();

        switch (sUriMatcher.match(uri)) {
            case WEATHERS:
                responseCursor = db
                        .query(WeatherInfoTable.TABLE_NAME,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null,
                                sortOrder);

                responseCursor.setNotificationUri(getContext().getContentResolver(), uri);
                return responseCursor;
            case WEATHER_FOR_ID:
                responseCursor = db
                        .query(WeatherInfoTable.TABLE_NAME,
                                projection,
                                WeatherInfoTable._ID + " =?",
                                new String[]{String.valueOf(ContentUris.parseId(uri))},
                                null,
                                null,
                                sortOrder);


                responseCursor.setNotificationUri(getContext().getContentResolver(), uri);
                return responseCursor;

            default:
                throw new UnsupportedOperationException(UNKNOWN_URI + uri);
        }
    }


    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = myWeatherDbHelper.getWritableDatabase();
        Uri returnUri;
        switch (sUriMatcher.match(uri)) {
            case WEATHERS:
                long id = db.insert(WeatherInfoTable.TABLE_NAME, null, contentValues);
                if (id > 0) {
                    returnUri = WeatherInfoTable.buildWeatherInfoForId(id);
                } else {
                    throw new SQLException("Failed to insert row into: " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException(UNKNOWN_URI + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = myWeatherDbHelper.getWritableDatabase();
        int numDeleted;

        switch (sUriMatcher.match(uri)) {
            case WEATHERS: {
                numDeleted = db.delete(WeatherInfoTable.TABLE_NAME,
                        selection,
                        selectionArgs
                );
                break;
            }
            case WEATHER_FOR_ID: {
                numDeleted = db.delete(WeatherInfoTable.TABLE_NAME,
                        WeatherInfoTable._ID + " =?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            default:
                throw new UnsupportedOperationException(UNKNOWN_URI + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return numDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = myWeatherDbHelper.getWritableDatabase();
        int numUpdated = 0;

        if (contentValues == null) {
            throw new IllegalArgumentException("Cannot have null content values");
        }

        switch (sUriMatcher.match(uri)) {
            case WEATHERS:
                numUpdated = db.update(
                        WeatherInfoTable.TABLE_NAME,
                        contentValues,
                        selection,
                        selectionArgs);
                break;
            case WEATHER_FOR_ID:
                numUpdated = db.update(
                        WeatherInfoTable.TABLE_NAME,
                        contentValues,
                        WeatherInfoTable._ID + " =?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            default:
                throw new UnsupportedOperationException(UNKNOWN_URI + uri);
        }

        if (numUpdated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numUpdated;
    }
}
