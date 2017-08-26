package com.kadirkertis.myweather.mainscreen.di;

import com.kadirkertis.myweather.AppComponent;
import com.kadirkertis.myweather.mainscreen.MainActivity;
import com.kadirkertis.myweather.mainscreen.MainFragment;

import dagger.Component;

/**
 * Created by Kadir Kertis on 25.7.2017.
 */
@MainActivityScope
@Component(modules = MainActivityModule.class, dependencies = AppComponent.class)
public interface MainActivityComponent {

    void inject(MainFragment target);
}
