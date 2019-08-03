package org.me.gcu.coursework.weatherapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.me.gcu.coursework.weatherapplication.dao.XMLParser;
import org.me.gcu.coursework.weatherapplication.model.WeatherLocation;
import org.me.gcu.coursework.weatherapplication.repo.WeatherLocationRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final Map<String,String> location_ids = new HashMap<String, String>()
    {{
        put("Glasgow", "2648579");
        put("London", "2643743");
        put("New York", "5128581");
        put("Oman", "287286");
        put("Mauritius", "934154");
        put("Bangladesh", "1185241");
        put("Maseru", "932505");
    }} ;
    private static final String ENDPOINT = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new LoadXML().execute(ENDPOINT);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));

    }

    private class LoadXML extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {
            for( String loc : location_ids.keySet()){
                WeatherLocation location = XMLParser.loadXmlFromInternet(urls[0] + location_ids.get(loc),loc);
                WeatherLocationRepository.add(location);
            }
            System.out.println("Completed!");
            return "Completed! hurray!";
        }

        protected void onPostExecute(String result) {
            List<WeatherLocation> list = WeatherLocationRepository.getLocationList();
            for (int i = 0; i < list.size() ; i++) {
                System.out.println(list.get(i).getName());
            }
        }
    }


}
