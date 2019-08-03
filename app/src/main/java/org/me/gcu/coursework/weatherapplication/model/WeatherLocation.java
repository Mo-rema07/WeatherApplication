package org.me.gcu.coursework.weatherapplication.model;

import java.util.ArrayList;
import java.util.List;

public class WeatherLocation {
    private String name;
    private String weatherIconURL;
    private List<DailyForecast> three_day_forecast;

    public WeatherLocation() {
        this.name = "Maseru";
        this.weatherIconURL = "";
        this.three_day_forecast = new ArrayList<>();
    }

    public WeatherLocation(String name, String weatherIconURL, List<DailyForecast> three_day_forecast) {
        this.name = name;
        this.weatherIconURL = weatherIconURL;
        this.three_day_forecast = three_day_forecast;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeatherIconURL() {
        return weatherIconURL;
    }

    public void setWeatherIconURL(String weatherIconURL) {
        this.weatherIconURL = weatherIconURL;
    }

    public List<DailyForecast> getThree_day_forecast() {
        return three_day_forecast;
    }

    public void setThree_day_forecast(List<DailyForecast> three_day_forecast) {
        this.three_day_forecast = three_day_forecast;
    }
}
