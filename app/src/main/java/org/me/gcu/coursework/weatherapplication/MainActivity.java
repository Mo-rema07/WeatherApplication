package org.me.gcu.coursework.weatherapplication;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

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

}

//TODO: Make sure all the static fields and methods need to be static
//TODO: Clean up comments