package org.me.gcu.coursework.weatherapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.me.gcu.coursework.weatherapplication.repo.WeatherLocationRepository;

public class ViewPagerAdapter extends FragmentPagerAdapter {


    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return new SummaryFragment(i);
    }

    @Override
    public int getCount() {
        return WeatherLocationRepository.getInstance().getLocationList().size();
    }
}
