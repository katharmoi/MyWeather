package com.kadirkertis.myweather.mainscreen;

/**
 * Created by Kadir Kertis on 17.7.2017.
 */

public interface MainPresenter {
    void setView(MainView view);

    void loadWeatherInfo();
    void loadCurrentWeather();
}
