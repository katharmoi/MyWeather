package com.kadirkertis.myweather.forecastrestservice.model;

/**
 * Created by Kadir Kertis on 17.7.2017.
 */

public class Forecast {

    private String timezone;

    private CurrentConditions currently;

    private String longitude;

    private String latitude;

    public String getTimezone ()
    {
        return timezone;
    }

    public void setTimezone (String timezone)
    {
        this.timezone = timezone;
    }


    public CurrentConditions getCurrently ()
    {
        return currently;
    }

    public void setCurrently (CurrentConditions currently)
    {
        this.currently = currently;
    }

    public String getLongitude ()
    {
        return longitude;
    }

    public void setLongitude (String longitude)
    {
        this.longitude = longitude;
    }

    public String getLatitude ()
    {
        return latitude;
    }

    public void setLatitude (String latitude)
    {
        this.latitude = latitude;
    }
}
