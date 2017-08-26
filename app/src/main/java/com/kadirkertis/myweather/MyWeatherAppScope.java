package com.kadirkertis.myweather;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Kadir Kertis on 25.7.2017.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface MyWeatherAppScope {
}
