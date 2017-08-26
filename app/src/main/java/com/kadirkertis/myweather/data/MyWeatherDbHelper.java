package com.kadirkertis.myweather.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.kadirkertis.myweather.data.MyWeatherDbContract.WeatherInfoTable.*;

/**
 * Created by Kadir Kertis on 17.7.2017.
 */

public class MyWeatherDbHelper extends SQLiteOpenHelper {

    private static final int MY_WEATHER_DB_VERSION =1;
    private static final String MY_WEATHER_DB_NAME = "my_weather.db";

    public MyWeatherDbHelper(Context context){
        super(context,MY_WEATHER_DB_NAME,null,MY_WEATHER_DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_WEATHER_TABLE =
                "CREATE TABLE "
                +TABLE_NAME
                +" ("
                +_ID +" INTEGER PRIMARY KEY,"
                +COLUMN_PLACE_NAME +" TEXT NOT NULL,"
                +COLUMN_TMP  +" INTEGER NOT NULL,"
                +COLUMN_HUMIDITY +" INTEGER,"
                +COLUMN_CONDITION +" TEXT "
                +");";

        sqLiteDatabase.execSQL(SQL_CREATE_WEATHER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
