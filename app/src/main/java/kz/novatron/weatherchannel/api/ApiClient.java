package kz.novatron.weatherchannel.api;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import kz.novatron.weatherchannel.models.GetWeatherResponse;
import rx.Observer;

/*
 * Created by smustafa on 25.01.2018.
 */


public class ApiClient {

    private WeatherEndPointInterface zapisEndPointInterface;
    private ClientConnect clientConnect;

    private static ApiClient instance;
    private final String APPID = "3ac99a86bb93188b6299f7b0e1dc624c";
    private final String UNITS = "metric";

    public static ApiClient get() {
        if(instance == null){
            instance = new ApiClient();
        }
        return instance;
    }

    public ApiClient() {
        clientConnect = new ClientConnect();

        clientConnect.initRestApi();

        zapisEndPointInterface = clientConnect.getWeatherEndPointInterface();
    }

    public Observable<GetWeatherResponse> getWeatherByCity(final String cityName) {

        Observable<GetWeatherResponse> o = Observable.create(new ObservableOnSubscribe<GetWeatherResponse>() {
            @Override
            public void subscribe(final ObservableEmitter<GetWeatherResponse> s) throws Exception {
                zapisEndPointInterface.getWeather(cityName, UNITS, APPID).subscribe(new Observer<GetWeatherResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        s.onError(e);
                    }

                    @Override
                    public void onNext(GetWeatherResponse getWeatherResponse) {
                        s.onNext(getWeatherResponse);
                    }
                });
            }
        });
        return o;
    }

}

