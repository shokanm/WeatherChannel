package kz.novatron.weatherchannel.models;

/**
 * Created by smustafa on 25.01.2018.
 */

public class CityWeather {
    private String cityName;
    private String degree;

    public CityWeather(String cityName, String degree) {
        this.cityName = cityName;
        this.degree = degree;
    }

    public CityWeather() {
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }
}
