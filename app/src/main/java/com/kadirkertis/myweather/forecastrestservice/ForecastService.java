package com.kadirkertis.myweather.forecastrestservice;

import com.kadirkertis.myweather.forecastrestservice.model.CurrentConditions;
import com.kadirkertis.myweather.forecastrestservice.model.Forecast;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Kadir Kertis on 17.7.2017.
 */

public interface ForecastService {
    Single<Forecast> getForecastForLocation(String latitude, String longitude);
}
