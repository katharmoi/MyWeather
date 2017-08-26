package com.kadirkertis.myweather.androidservices;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.kadirkertis.myweather.MyWeatherApp;
import com.kadirkertis.myweather.forecastrestservice.ForecastService;

import javax.inject.Inject;

/**
 * Created by Kadir Kertis on 24.7.2017.
 */

public class WeatherService extends Service {
     private static final String TAG = WeatherService.class.getSimpleName();

    @Inject
    ForecastService forecastService;

    @Override
    public void onCreate() {
        super.onCreate();

        ((MyWeatherApp)getApplication()).getAppComponent().inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("TAG","Weather Service Started");

        forecastService.getForecastForLocation("a","b");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent ıntent) {
        return null;
    }
}
