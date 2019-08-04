//package org.me.gcu.coursework.weatherapplication;
//
//
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentPagerAdapter;
//import androidx.viewpager.widget.PagerAdapter;
//import androidx.viewpager.widget.ViewPager;
//
//import org.me.gcu.coursework.weatherapplication.model.DailyForecast;
//import org.me.gcu.coursework.weatherapplication.model.WeatherLocation;
//import org.me.gcu.coursework.weatherapplication.repo.WeatherLocationRepository;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.Locale;
//
//public   class ViewPagerAdapter extends PagerAdapter {
//    @Override
//    public int getCount() {
//        //Return total pages, here one for each data item
//        return locationData.length;
//    }
//
//    //Create the given page (indicated by position)
//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        String dayOfTheWeek = new SimpleDateFormat("EEEE dd, MMMM, yyyy", Locale.getDefault()).format(new Date());
//        WeatherLocation locationDatum = locationData[position];
//        View page = inflater.inflate(R.layout.view_location, null);
//        ListView listView = (ListView) findViewById(R.id.listView);
//        TextView day = (TextView) findViewById(R.id.dayOfTheWeek);
//        TextView locationName = (TextView) findViewById(R.id.location);
////        TextView temperature = (TextView) findViewById(R.id.temperature);
//        day.setText(dayOfTheWeek);
//        locationName.setText(locationDatum.getName());
//        ArrayList<DailyForecast> three_day_forecast = (ArrayList<DailyForecast>) locationDatum.getThree_day_forecast();
//        int size = three_day_forecast.size();
//        DailyForecast[] listItem = new DailyForecast[size];
//        for (int i = 0; i < size; i++) {
//            listItem[i] = three_day_forecast.get(i);
//        }
//        final DailyForecastAdapter adapter;
//        adapter = new DailyForecastAdapter(MainActivity.class/**/, three_day_forecast);
//        listView.setAdapter(adapter);
//        ((ViewPager) container).addView(page, 0);
//        return page;
//    }
//
//    @Override
//    public boolean isViewFromObject(View arg0, Object arg1) {
//        //See if object from instantiateItem is related to the given view
//        //required by API
//        return arg0 == (View) arg1;
//    }
//
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        ((ViewPager) container).removeView((View) object);
//        object = null;
//    }
//}
//
