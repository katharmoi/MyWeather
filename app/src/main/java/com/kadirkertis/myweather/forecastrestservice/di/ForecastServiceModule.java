package com.kadirkertis.myweather.forecastrestservice.di;

import com.kadirkertis.myweather.MyWeatherAppScope;
import com.kadirkertis.myweather.forecastrestservice.ForecastApi;
import com.kadirkertis.myweather.forecastrestservice.ForecastService;
import com.kadirkertis.myweather.forecastrestservice.ForecastServiceImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Kadir Kertis on 25.7.2017.
 */

@Module
public class ForecastServiceModule {

    @Provides
    @MyWeatherAppScope
    public ForecastService provideForecastService(ForecastApi api){
        return new ForecastServiceImpl(api);
    }
}
