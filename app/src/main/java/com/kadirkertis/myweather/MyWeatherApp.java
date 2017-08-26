package com.kadirkertis.myweather;

import android.app.Activity;
import android.app.Application;

/**
 * Created by Kadir Kertis on 17.7.2017.
 */

public class MyWeatherApp extends Application {

    private AppComponent appComponent;

    public static MyWeatherApp getApplication(Activity activity){
        return (MyWeatherApp)activity.getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //You only need modules that have constructor arguments

//        appComponent = DaggerAppComponent.builder()
//                .androidModule(new AndroidModule(this))
//                .appModule(new AppModule())
//                .build();

        //So above code turns to
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent(){
        return appComponent;
    }
}
