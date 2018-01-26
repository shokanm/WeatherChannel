package kz.novatron.weatherchannel.api;


import kz.novatron.weatherchannel.models.GetWeatherResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by smustafa on 25.01.2018.
 */

public interface WeatherEndPointInterface {
    @GET("weather")
    Observable<GetWeatherResponse> getWeather(@Query("q") String cityName,
                                              @Query("units") String units,
                                              @Query("APPID") String appid);
}
