package com.kadirkertis.myweather;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import com.kadirkertis.myweather.forecastrestservice.ForecastService;
import com.kadirkertis.myweather.forecastrestservice.ForecastServiceImpl;
import com.kadirkertis.myweather.repository.WeatherRepository;
import com.kadirkertis.myweather.repository.WeatherRepositoryImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Kadir Kertis on 17.7.2017.
 */

@Module
public class AppModule {

    private  final Context context;

    public AppModule(Application app) {
        this.context = app;
    }

    @Provides
    @MyWeatherAppScope
    public  Context provideAppContext(){return context;}

    @Provides
    @MyWeatherAppScope
    public Resources provideResources(){
        return  context.getResources();
    }

    @Provides
    @MyWeatherAppScope
    public SharedPreferences provideSharedPreferences(){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    @MyWeatherAppScope
    public WeatherRepository provideWeatherRepository(){
        return new WeatherRepositoryImpl();
    }


}
