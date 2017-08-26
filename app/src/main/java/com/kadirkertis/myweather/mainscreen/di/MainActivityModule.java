package com.kadirkertis.myweather.mainscreen.di;

import android.content.Context;

import com.kadirkertis.myweather.forecastrestservice.ForecastService;
import com.kadirkertis.myweather.mainscreen.MainActivity;
import com.kadirkertis.myweather.mainscreen.MainPresenter;
import com.kadirkertis.myweather.mainscreen.MainPresenterImpl;
import com.kadirkertis.myweather.repository.WeatherRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Kadir Kertis on 25.7.2017.
 */
@Module
public class MainActivityModule {

    private final MainActivity context;

    public MainActivityModule(MainActivity context) {
        this.context = context;
    }

    @Provides
    @MainActivityScope
    public Context provideMainActivityContext(){
        return context;
    }

    @Provides
    @MainActivityScope
    public MainPresenter provideMainPresenter(WeatherRepository weatherRepository, ForecastService forecastService){
        return new MainPresenterImpl(weatherRepository,forecastService);
    }
}
