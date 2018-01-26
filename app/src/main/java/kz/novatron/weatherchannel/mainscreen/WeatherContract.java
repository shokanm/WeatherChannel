package kz.novatron.weatherchannel.mainscreen;

import java.util.List;

import kz.novatron.weatherchannel.models.CityWeather;

/**
 * Created by smustafa on 25.01.2018.
 */

public interface WeatherContract {
    interface View{
        void displayCities(List<CityWeather> cityWeatherList);
        void displaySearchQuery(String queryText, boolean submit);
        void showProgressBar();
        void hideProgressBar();

        void showError();
    }
    interface Presenter{
        void subscribe(List<CityWeather> cityWeatherList);
        List<CityWeather> loadCityWeatherList();
        void getCacheValues();
        void truncateTable();
    }
}
