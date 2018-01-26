package kz.novatron.weatherchannel.mainscreen;

import android.content.Context;
import android.util.Log;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import kz.novatron.weatherchannel.SharedPreferencesManager;
import kz.novatron.weatherchannel.api.ApiClient;
import kz.novatron.weatherchannel.db.DBHelper;
import kz.novatron.weatherchannel.models.CityWeather;
import kz.novatron.weatherchannel.models.GetWeatherResponse;

/**
 * Created by smustafa on 25.01.2018.
 */

public class PresenterImpl implements WeatherContract.Presenter {
    private WeatherContract.View view;
    private Context context;
    private DBHelper dbHelper;
    private SharedPreferencesManager sharedPreferencesManager;
    private long CACHE_LIFE_TIME_ONE_HOUR_MILLIS = 3600000;

    public PresenterImpl(WeatherContract.View view, Context context, SharedPreferencesManager sharedPreferencesManager){
        this.view = view;
        this.context = context;
        this.sharedPreferencesManager = sharedPreferencesManager;
        dbHelper = DBHelper.getInstance(this.context);
    }

    @Override
    public void subscribe(final List<CityWeather> cityWeatherList) {
        final List<CityWeather> cityWeathers = new ArrayList<>();
        view.showProgressBar();
        for(final CityWeather cityWeather: cityWeatherList) {
            try {
                ApiClient.get().getWeatherByCity(cityWeather.getCityName())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<GetWeatherResponse>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(GetWeatherResponse getWeatherResponse) {
                                cityWeather.setDegree(getWeatherResponse.getMain().getTemp());
                                view.displayCities(cityWeathers);
                                try {
                                    for (CityWeather cityWeather : cityWeatherList) {
                                        dbHelper.saveWeatherData(cityWeather);
                                   }
                                    view.hideProgressBar();
                                    sharedPreferencesManager.setBlockTime(new Date());
                                } catch (ParseException pe) {
                                    view.hideProgressBar();
                                    Log.e("DB exception", pe.getMessage());
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.hideProgressBar();
                                view.showError();
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
            catch (Exception e){
                Log.e("exc111", e.getMessage());
            }
        }
    }


    @Override
    public List<CityWeather> loadCityWeatherList() {
        List<CityWeather> cityWeathers = new ArrayList<>();
        try {
            cityWeathers = DBHelper.getInstance(context).getWeatherDataList();
        }catch (ParseException pe){
            Log.e("DB exception", pe.getMessage());
        }
        return cityWeathers;
    }

    @Override
    public void getCacheValues(){
        if(sharedPreferencesManager.isFirstStartApp()){
            sharedPreferencesManager.setBlockTime(new Date());
            sharedPreferencesManager.setIsFirstStartApp(false);
        }
        else if ((new Date().getTime() - sharedPreferencesManager.getBlockTime() <= CACHE_LIFE_TIME_ONE_HOUR_MILLIS) || sharedPreferencesManager.getBlockTime() == 0) {
            List<CityWeather> cityWeathers = loadCityWeatherList();
            if(cityWeathers.size()>0)
                view.displayCities(cityWeathers);
            view.displaySearchQuery(sharedPreferencesManager.getLastQueryText(), false);
        }
        else {
            view.displaySearchQuery(sharedPreferencesManager.getLastQueryText(), true);
            /*
                если время жизни кэша привышено, то запускаем обновление
             */
        }
    }

    @Override
    public void truncateTable() {
        dbHelper.truncateTable();
    }
}
