package com.kadirkertis.myweather.repository;

import com.kadirkertis.myweather.forecastrestservice.model.CurrentConditions;

/**
 * Created by Kadir Kertis on 28.7.2017.
 */

public interface WeatherRepository {

    CurrentConditions getWeatherInfo();
    void setWeatherInfo(CurrentConditions conditions);

}
