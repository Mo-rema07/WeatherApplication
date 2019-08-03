package org.me.gcu.coursework.weatherapplication.dao;

import android.util.Xml;

import org.me.gcu.coursework.weatherapplication.model.DailyForecast;
import org.me.gcu.coursework.weatherapplication.model.WeatherLocation;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class XMLParser {
    private static final String ns = null;
    public static WeatherLocation location;


    private static WeatherLocation readRSS(XmlPullParser parser, String locationName)
            throws XmlPullParserException, IOException {
        WeatherLocation location = null;
        parser.require(XmlPullParser.START_TAG, ns, "rss");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("channel")) {
                location = readChannel(parser, locationName);
                XMLParser.location = location;
            } else {
                skip(parser);
            }
        }
        return location;
    }

    private static WeatherLocation readChannel(XmlPullParser parser, String locationName)
            throws XmlPullParserException, IOException {
        List<DailyForecast> dailyForecasts = new ArrayList<>();
        String weatherIconURL = "";
        parser.require(XmlPullParser.START_TAG, ns, "channel");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("item")) {
                dailyForecasts = readItem(parser);
            } else if (name.equals("image")) {
                weatherIconURL = readImage(parser);
            } else {
                skip(parser);
            }
        }
        return new WeatherLocation(locationName, weatherIconURL, dailyForecasts);
    }

    private static String readImage(XmlPullParser parser) throws XmlPullParserException, IOException {
        String iconURL = "";
        parser.require(XmlPullParser.START_TAG, ns, "image");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("url")) {
                iconURL = readURL(parser);
            } else {
                skip(parser);
            }
        }
        return iconURL;
    }



    private static String readURL(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "url");
        String summary = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "url");
        return summary;
    }


    private static String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private static List<DailyForecast> readItem(XmlPullParser parser)
            throws XmlPullParserException, IOException {
        List<DailyForecast> forecasts = new ArrayList<DailyForecast>();
        parser.require(XmlPullParser.START_TAG, ns, "item");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String description = "";
            String title = "";
            String name = parser.getName();
            if (name.equals("title")) {
                title = readText(parser);
            } else if (name.equals("description")){
                description = readText(parser);
            } else {
                skip(parser);
            }

            HashMap<String,String> properties = readProps(description);
            if (properties!=null){
                String max_temp = properties.get("max_temp");
                String min_temp = properties.get("min_temp");
                String wind_speed = properties.get("wind_speed");
                String wind_dir = properties.get("wind_dir");
                String the_skies = readRain(title);
                String visibility = properties.get("visibility");
                String pressure = properties.get("pressure");
                String humidity = properties.get("humidity");
                forecasts.add(
                        new DailyForecast(max_temp,min_temp,wind_speed,wind_dir,the_skies,visibility,
                                pressure,humidity));
            }
        }
        return forecasts;
    }

    private static String readRain(String title){
        if (title.equals("")){
            return "";
        }
        else{
            String[] listOfProps = title.split(",");
            String[] today = listOfProps[0].split(":");
            return today[1];
        }
    }

    private static HashMap<String,String> readProps(String des){
        HashMap<String,String> propsList = new HashMap<>();
        List<String> keys = Arrays.asList("sunrise","wind_speed","humidity","wind_dir","pressure",
                "sunset","max_temp","visibility","pollution","min_temp","uv_risk");
        if (des.equals("")){
            return null;
        }
        else {
            String[] listOfProps = des.split(",");
            int length = listOfProps.length;
            for (int i = 0; i < length; i++) {
                String[] prop = listOfProps[i].split(":");
                propsList.put(keys.get(i),prop[1]);
            }
        }
        for (String prop: propsList.keySet()){
        }
        return propsList;
    }

    private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
       public static WeatherLocation loadXmlFromInternet(final String urlString, String locationName){
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            conn.connect();
            InputStream stream = conn.getInputStream();
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(stream, null);
            parser.nextTag();
            WeatherLocation weatherLocation = readRSS(parser, locationName);
            stream.close();
            return weatherLocation;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return null;
    }
}