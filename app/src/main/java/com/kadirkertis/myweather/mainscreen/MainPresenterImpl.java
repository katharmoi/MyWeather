package com.kadirkertis.myweather.mainscreen;

import android.util.Log;

import com.kadirkertis.myweather.forecastrestservice.ForecastService;
import com.kadirkertis.myweather.forecastrestservice.model.CurrentConditions;
import com.kadirkertis.myweather.repository.WeatherRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Kadir Kertis on 17.7.2017.
 */

public class MainPresenterImpl implements MainPresenter {
    private static final String TAG = MainPresenterImpl.class.getSimpleName();
    private MainView view;
    private WeatherRepository repository;
    private CurrentConditions conditions;
    private ForecastService forecastService;

    public MainPresenterImpl(WeatherRepository repository, ForecastService forecastService) {

        this.repository = repository;
        this.forecastService = forecastService;
    }

    @Override
    public void setView(MainView view) {
        this.view = view;
    }

    @Override
    public void loadWeatherInfo() {
        if (view == null) {
            throw new ViewNotFoundException();
        }
        conditions = repository.getWeatherInfo();

        if (conditions == null) {
            view.displayWeatherLoadingError();
        } else {
            view.displayTemp(conditions.getTemperature());
        }

    }

    @Override
    public void loadCurrentWeather() {
        forecastService.getForecastForLocation("38.41885", "27.12872")
                .subscribeOn(Schedulers.io())
                .map(fs -> fs.getCurrently().getTemperature())
                .doOnError(error -> Log.d(TAG, error.getMessage()))
                .onErrorReturn(error -> "Cant get Temperature")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::displayTemp);
    }


}
