package com.kadirkertis.myweather;

import com.kadirkertis.myweather.forecastrestservice.ForecastService;
import com.kadirkertis.myweather.forecastrestservice.ForecastServiceImpl;
import com.kadirkertis.myweather.forecastrestservice.di.ForecastServiceModule;
import com.kadirkertis.myweather.mainscreen.MainPresenter;
import com.kadirkertis.myweather.repository.WeatherRepository;
import com.kadirkertis.myweather.androidservices.WeatherService;

import dagger.Component;

/**
 * Created by Kadir Kertis on 17.7.2017.
 */

@MyWeatherAppScope
@Component(modules = {AppModule.class, ForecastServiceModule.class, HttpModule.class})
public interface AppComponent {
    void  inject(WeatherService target);
    void inject(ForecastServiceImpl target);
    WeatherRepository weatherRepository();
    ForecastService forecastService();
}
