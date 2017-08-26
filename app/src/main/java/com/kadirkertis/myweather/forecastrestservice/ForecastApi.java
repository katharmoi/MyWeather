package com.kadirkertis.myweather.forecastrestservice;

import com.kadirkertis.myweather.forecastrestservice.model.CurrentConditions;
import com.kadirkertis.myweather.forecastrestservice.model.Forecast;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Kadir Kertis on 17.7.2017.
 */

public interface ForecastApi {
    @GET("{latitude},{longitude}")
    Single<Forecast> getForecastFor(@Path("latitude") String latitude,
                                             @Path("longitude") String longitude);
}
