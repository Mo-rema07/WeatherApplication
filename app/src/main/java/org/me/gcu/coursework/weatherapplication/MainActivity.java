package org.me.gcu.coursework.weatherapplication;

import android.content.Context;
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
    WeatherLocation[] locationData;          //Stores the text to swipe.
    LayoutInflater inflater;    //Used to create individual pages
    ViewPager vp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        new LoadXML().execute();
//            WeatherLocation location = WeatherLocationRepository.getInstance().getLocationList().get(0);

//        locationData = (WeatherLocation[]) WeatherLocationRepository.getInstance().getLocationList().toArray();
//        //get an inflater to be used to create single pages
//        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        //Reference ViewPager defined in activity
//        vp = (ViewPager) findViewById(R.id.viewPager);
//        //set the adapter that will create the individual pages
//        vp.setAdapter(new ViewPagerAdapter());

    }

    private void startUI(WeatherLocation location){
        String dayOfTheWeek = new SimpleDateFormat("EEEE dd, MMMM, yyyy", Locale.getDefault()).format(new Date());
        TextView day = (TextView) findViewById(R.id.dayOfTheWeek);
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
            setContentView(R.layout.view_location);
            WeatherLocation location = WeatherLocationRepository.getInstance().getLocationList().get(2);
            startUI(location);

//            locationData = (WeatherLocation[]) WeatherLocationRepository.getInstance().getLocationList().toArray(new WeatherLocation[0]);
//            //get an inflater to be used to create single pages
//            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            //Reference ViewPager defined in activity
//            vp = (ViewPager) findViewById(R.id.viewPager);
//            //set the adapter that will create the individual pages
//            vp.setAdapter(new ViewPagerAdapter());
        }
    }

    private class ViewPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            //Return total pages, here one for each data item
            return locationData.length;
        }

        //Create the given page (indicated by position)
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View page = inflater.inflate(R.layout.view_location, null);
            WeatherLocation locationDatum = locationData[position];
            startUI(locationDatum);
            ((ViewPager) container).addView(page, 0);
            return page;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            //See if object from instantiateItem is related to the given view
            //required by API
            return arg0 == (View) arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
            object = null;
        }
    }
}


//TODO: Make sure all the static fields and methods need to be static
//TODO: Clean up comments
