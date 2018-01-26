package kz.novatron.weatherchannel.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by smustafa on 25.01.2018.
 */

public class GetWeatherResponse {
    @SerializedName("main")
    private Main main;

    public GetWeatherResponse(Main main) {
        this.main = main;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
