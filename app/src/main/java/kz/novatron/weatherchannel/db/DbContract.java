package kz.novatron.weatherchannel.db;

import android.provider.BaseColumns;

/**
 * Created by smustafa on 25.01.2018.
 */

public class DbContract {
    static class CityWeatherEntry implements BaseColumns {
        static final String TABLE_NAME = "city_weather";
        static final String COLUMN_NAME_CITY_NAME = "city_name";
        static final String COLUMN_NAME_DEGREE = "degree";

    }
}
