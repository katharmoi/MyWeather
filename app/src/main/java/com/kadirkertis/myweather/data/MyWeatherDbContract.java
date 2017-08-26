package com.kadirkertis.myweather.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Kadir Kertis on 17.7.2017.
 */

public final class MyWeatherDbContract {

    public static final String CONTENT_AUTHORITY ="com.kadirkertis.myweather";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" +CONTENT_AUTHORITY);

    public static final String PATH_WEATHER_INFO ="weather_info";

    public static final class  WeatherInfoTable implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_WEATHER_INFO)
                .build();

        //MIME TYPES
        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE +"/" +CONTENT_AUTHORITY
                +"/" +PATH_WEATHER_INFO;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
                +"/" +CONTENT_AUTHORITY
                + "/" +PATH_WEATHER_INFO;

        public static final String TABLE_NAME = "weather_info";

        public static final String COLUMN_PLACE_NAME ="place_name";
        public static final String COLUMN_TMP = "temperature";
        public static final String COLUMN_HUMIDITY = "humidity";
        public static  final String COLUMN_CONDITION ="condition";

        public static  Uri buildWeatherInfoForId(long id){
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }
    }
}
