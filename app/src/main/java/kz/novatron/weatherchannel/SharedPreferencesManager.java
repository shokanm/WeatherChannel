package kz.novatron.weatherchannel;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

/**
 * Created by smustafa on 25.01.2018.
 */


public class SharedPreferencesManager {
    private static final String SHARED_PREF_FILE_NAME = "ALM_WEATHER_CHNL";
    private SharedPreferences sharedpreferences;
    private static final String BLOCK_TIME = "BLOCK_TIME";
    private Context mContext;
    private String FIRST_START_APP = "FIRST_START_APP";
    private String LAST_QUERY_TEXT = "LAST_QUERY_TEXT";

    public SharedPreferencesManager(Context context) {
        this.sharedpreferences = context.getSharedPreferences(SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void setBlockTime(Date blockTime) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putLong(BLOCK_TIME, blockTime.getTime());
        editor.apply();
    }

    public long getBlockTime() {
        return sharedpreferences.getLong(BLOCK_TIME, new Date().getTime());
    }

    public void setIsFirstStartApp(boolean isFirst){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(FIRST_START_APP, isFirst);
        editor.apply();
    }

    public boolean isFirstStartApp(){
        return sharedpreferences.getBoolean(FIRST_START_APP, true);
    }

    public void setLastQueryText(CharSequence lastQueryText) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(LAST_QUERY_TEXT, lastQueryText.toString());
        editor.apply();
    }

    public String getLastQueryText(){
        return sharedpreferences.getString(LAST_QUERY_TEXT, "");
    }
}
