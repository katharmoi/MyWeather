package com.kadirkertis.myweather.forecastrestservice;

import com.kadirkertis.myweather.MyWeatherApp;
import com.kadirkertis.myweather.forecastrestservice.model.Forecast;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by Kadir Kertis on 17.7.2017.
 */

public class ForecastServiceImpl implements ForecastService {

    private ForecastApi forecastApi;

    public ForecastServiceImpl(ForecastApi api){
        this.forecastApi = api;
    }
    @Override
    public Single<Forecast> getForecastForLocation(String latitude, String longitude) {

        return forecastApi.getForecastFor(latitude,longitude);

    }
}
