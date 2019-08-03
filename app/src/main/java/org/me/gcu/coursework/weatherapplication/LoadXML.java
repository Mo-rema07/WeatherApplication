package org.me.gcu.coursework.weatherapplication;

import android.os.AsyncTask;

import org.me.gcu.coursework.weatherapplication.dao.XMLParser;
import org.me.gcu.coursework.weatherapplication.model.DailyForecast;
import org.me.gcu.coursework.weatherapplication.model.WeatherLocation;
import org.me.gcu.coursework.weatherapplication.repo.WeatherLocationRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadXML extends AsyncTask<String, Void, String> {
    private static final String ENDPOINT = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/";
    private static final Map<String,String> LOCATION_IDS = new HashMap<String, String>()
    {{
        put("Glasgow", "2648579");
        put("London", "2643743");
        put("New York", "5128581");
        put("Oman", "287286");
        put("Mauritius", "934154");
        put("Bangladesh", "1185241");
        put("Maseru", "932505");
    }} ;

    @Override
    protected String doInBackground(String... urls) {
        for( String loc : LOCATION_IDS.keySet()){
            WeatherLocation location = XMLParser.loadXmlFromInternet( ENDPOINT + LOCATION_IDS.get(loc),loc);
            WeatherLocationRepository.getInstance().add(location);
        }
        return null;
    }

    protected void onPostExecute(String result) {
        WeatherLocation location = WeatherLocationRepository.getInstance().getLocationList().get(0);
        List<DailyForecast> three_day_forecast = location.getThree_day_forecast();
        System.out.println(location.getName());
        for (DailyForecast df : three_day_forecast){
            System.out.println("Humidity is "+df.getHumidity());
        }
        System.out.println(three_day_forecast.size());
    }
}
