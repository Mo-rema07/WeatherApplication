package org.me.gcu.coursework.weatherapplication.model;

public class DailyForecast {
    private String max_temp;
    private String min_temp;
    private String wind_speed;
    private String wind_dir;
    private String the_skies;
    private String visibility;
    private String pressure;
    private String humidity;

    public DailyForecast(String max_temp, String min_temp, String wind_speed, String wind_dir,
                         String the_skies, String visibility, String pressure, String humidity) {
        this.max_temp = max_temp;
        this.min_temp = min_temp;
        this.wind_speed = wind_speed;
        this.wind_dir = wind_dir;
        this.the_skies = the_skies;
        this.visibility = visibility;
        this.pressure = pressure;
        this.humidity = humidity;
    }

    public String getMax_temp() {
        return max_temp;
    }

    public void setMax_temp(String max_temp) {
        this.max_temp = max_temp;
    }

    public String getMin_temp() {
        return min_temp;
    }

    public void setMin_temp(String min_temp) {
        this.min_temp = min_temp;
    }

    public String getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(String wind_speed) {
        this.wind_speed = wind_speed;
    }

    public String getWind_dir() {
        return wind_dir;
    }

    public void setWind_dir(String wind_dir) {
        this.wind_dir = wind_dir;
    }

    public String getThe_skies() {
        return the_skies;
    }

    public void setThe_skies(String the_skies) {
        this.the_skies = the_skies;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }
}
