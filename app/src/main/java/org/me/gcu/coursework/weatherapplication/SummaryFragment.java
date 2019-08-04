package org.me.gcu.coursework.weatherapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.me.gcu.coursework.weatherapplication.model.WeatherLocation;
import org.me.gcu.coursework.weatherapplication.repo.WeatherLocationRepository;

import java.util.List;

public class SummaryFragment extends Fragment {
    int position;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        super.onCreateView(inflater,container,savedInstanceState);
        View rootView = inflater.inflate(R.layout.view_location,container,false);
        return rootView;
    }
}
