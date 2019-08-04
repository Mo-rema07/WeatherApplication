package org.me.gcu.coursework.weatherapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.button.MaterialButton;

import org.me.gcu.coursework.weatherapplication.dao.XMLParser;
import org.me.gcu.coursework.weatherapplication.model.DailyForecast;
import org.me.gcu.coursework.weatherapplication.model.WeatherLocation;
import org.me.gcu.coursework.weatherapplication.repo.WeatherLocationRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static int locationIndex = 0;
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
    TextView day, locationName;
    WeatherLocation[] locationData;          //Stores the text to swipe.
    LayoutInflater inflater;    //Used to create individual pages
    ViewPager vp;
    MaterialButton nextButton, prevButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        new LoadXML().execute();

    }

    private void startUI(WeatherLocation location){
        String dayOfTheWeek = new SimpleDateFormat("EEEE dd, MMMM, yyyy", Locale.getDefault()).format(new Date());
        day = (TextView) findViewById(R.id.dayOfTheWeek);
        day.setText(dayOfTheWeek);
        listView = (ListView) findViewById(R.id.listView);
        locationName = (TextView) findViewById(R.id.location);
        locationName.setText(location.getName());
        ArrayList<DailyForecast> three_day_forecast = (ArrayList<DailyForecast>) location.getThree_day_forecast();
        int size = three_day_forecast.size();
        listItem = new DailyForecast[size];
        for (int i = 0; i < size; i++) {
            listItem[i] = three_day_forecast.get(i);
        }
        final DailyForecastAdapter adapter;
        adapter = new DailyForecastAdapter(MainActivity.this, three_day_forecast);
        listView.setAdapter(adapter);
        nextButton = (MaterialButton) findViewById(R.id.nextButton);
        prevButton = (MaterialButton) findViewById(R.id.previousButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size1 = WeatherLocationRepository.getInstance().getLocationList().size();
                locationIndex++;
                if (locationIndex >= size1){
                    locationIndex=0;
                }
                startUI(WeatherLocationRepository.getInstance().getLocationList().get(locationIndex));
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              int size1 = WeatherLocationRepository.getInstance().getLocationList().size();
                locationIndex--;
                if (locationIndex < 0){
                    locationIndex=size1-1;
                }
                startUI(WeatherLocationRepository.getInstance().getLocationList().get(locationIndex));
            }
        });
    }
    private class LoadXML extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... urls) {
            for (String loc : LOCATION_IDS.keySet()) {
                WeatherLocation location = XMLParser.loadXmlFromInternet(ENDPOINT + LOCATION_IDS.get(loc), loc);
                WeatherLocationRepository.getInstance().add(location);
            }
            return null;
        }

        protected void onPostExecute(String result) {
            setContentView(R.layout.activity_main);
            WeatherLocation location = WeatherLocationRepository.getInstance().getLocationList().get(2);
            startUI(location);
        }
    }
}


//TODO: Make sure all the static fields and methods need to be static
//TODO: Clean up comments
