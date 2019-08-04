package org.me.gcu.coursework.weatherapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.me.gcu.coursework.weatherapplication.dao.XMLParser;
import org.me.gcu.coursework.weatherapplication.model.DailyForecast;
import org.me.gcu.coursework.weatherapplication.model.WeatherLocation;
import org.me.gcu.coursework.weatherapplication.repo.WeatherLocationRepository;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
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
    ListView listView;
    DailyForecast[] listItem;
    TextView day, locationName, temperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry",
                "WebOS","Ubuntu","Windows7","Max OS X"};

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new LoadXML().execute();

//        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
//        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
//
//        ArrayAdapter adapter = new ArrayAdapter<String>(this,
//                R.layout.view_location, mobileArray);
//
//        ListView listView = (ListView) findViewById(R.id.list_view);
//        listView.setAdapter(adapter);
    }
    private class LoadXML extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... urls) {
            for( String loc : LOCATION_IDS.keySet()){
                WeatherLocation location = XMLParser.loadXmlFromInternet( ENDPOINT + LOCATION_IDS.get(loc),loc);
                WeatherLocationRepository.getInstance().add(location);
            }
            return null;
        }

        protected void onPostExecute(String result) {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd, MMMM, yyyy");
            Date d = new Date();
//            DateTimeFormatter dtf = new DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//            LocalDateTime now = LocalDateTime.now();
            String dayOfTheWeek = sdf.format(d);

            WeatherLocation location = WeatherLocationRepository.getInstance().getLocationList().get(0);
            listView=(ListView)findViewById(R.id.listView);
            day = (TextView)findViewById(R.id.day);
            locationName = (TextView)findViewById(R.id.location);
            temperature = (TextView)findViewById(R.id.temperature);
            day.setText(dayOfTheWeek);
            locationName.setText(location.getName());

            ArrayList<DailyForecast> three_day_forecast = (ArrayList<DailyForecast>) location.getThree_day_forecast();
            int size = three_day_forecast.size();
            listItem = new DailyForecast[size];
            for (int i = 0; i < size; i++) {
                listItem[i] = three_day_forecast.get(i);
            }
            final DailyForecastAdapter adapter = new DailyForecastAdapter(MainActivity.this,three_day_forecast);
            listView.setAdapter(adapter);
        }
    }

//TODO: Make sure all the static fields and methods need to be static
//TODO: Clean up comments

}