package org.me.gcu.coursework.weatherapplication.repo;

import org.me.gcu.coursework.weatherapplication.model.WeatherLocation;

import java.util.ArrayList;
import java.util.List;

public class WeatherLocationRepository {
    static List<WeatherLocation> locationList;
    private static final WeatherLocationRepository mInstance = new WeatherLocationRepository();

    private WeatherLocationRepository() {
        locationList = new ArrayList<>();
    }

    public static WeatherLocationRepository getInstance(){
        return mInstance;
    }

    public  List<WeatherLocation> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<WeatherLocation> locationList) {
        WeatherLocationRepository.locationList = locationList;
    }

    public void add(WeatherLocation location){
        locationList.add(location);
    }
}
