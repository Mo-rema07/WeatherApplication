package org.me.gcu.coursework.weatherapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.me.gcu.coursework.weatherapplication.model.DailyForecast;

import java.util.ArrayList;
import java.util.List;

public class DailyForecastAdapter extends ArrayAdapter {
    private Context mContext;
    private List<DailyForecast> forecastList= new ArrayList<> ();

    public DailyForecastAdapter(@NonNull Context context,  ArrayList<DailyForecast> list) {
        super(context, 0 , list);
        mContext = context;
        forecastList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.summary_location_card,parent,false);

        DailyForecast forecast = forecastList.get(position);

        TextView textView = (TextView)listItem.findViewById(R.id.the_skies);
        textView.setText(forecast.getThe_skies());

        TextView humidity = (TextView) listItem.findViewById(R.id.humidity);
        humidity.setText(forecast.getHumidity());

        return listItem;
    }
}
