package kz.novatron.weatherchannel.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import kz.novatron.weatherchannel.models.CityWeather;

/**
 * Created by smustafa on 25.01.2018.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "dar";
    private static final String CREATE_USER_TABLE = "CREATE TABLE " + DbContract.CityWeatherEntry.TABLE_NAME + " (" +
            DbContract.CityWeatherEntry._ID + " INTEGER PRIMARY KEY, " +
            DbContract.CityWeatherEntry.COLUMN_NAME_CITY_NAME + " TEXT, " +
            DbContract.CityWeatherEntry.COLUMN_NAME_DEGREE + " TEXT);";

    private static DBHelper instance;
    private final String LOG_TAG = this.getClass().getSimpleName();

    public static synchronized DBHelper getInstance(Context context){
        if(instance==null){
            instance = new DBHelper(context.getApplicationContext());
        }
        return instance;
    }
    private DBHelper(Context context){

        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void saveWeatherData(CityWeather cityWeather) throws ParseException {
        SQLiteDatabase writableDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DbContract.CityWeatherEntry.COLUMN_NAME_CITY_NAME, cityWeather.getCityName());
        cv.put(DbContract.CityWeatherEntry.COLUMN_NAME_DEGREE, cityWeather.getDegree());

        SQLiteDatabase readableDatabase = this.getReadableDatabase();
        String query = "select * from "+DbContract.CityWeatherEntry.TABLE_NAME+" where " + DbContract.CityWeatherEntry.COLUMN_NAME_CITY_NAME +" = '" + cityWeather.getCityName() + "'";
        Cursor cursor = readableDatabase.rawQuery(query, null);

        if (!(cursor.moveToFirst()) && (cursor.getCount() == 0)){
            long i = writableDatabase.insert(DbContract.CityWeatherEntry.TABLE_NAME,null,cv);
        } else {
            long i = writableDatabase.update(DbContract.CityWeatherEntry.TABLE_NAME,cv,DbContract.CityWeatherEntry.COLUMN_NAME_CITY_NAME+"='"+cityWeather.getCityName()+"'",null);
        }

        cursor.close();
        readableDatabase.close();
        writableDatabase.close();
    }

    public List<CityWeather> getWeatherDataList() throws ParseException {
        List<CityWeather> cityWeatherList = new ArrayList<>();
        SQLiteDatabase readableDatabase = this.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery("select * from "+DbContract.CityWeatherEntry.TABLE_NAME, null);
        while(cursor.moveToNext()){
            CityWeather cityWeather = new CityWeather();
            cityWeather.setCityName(cursor.getString(cursor.getColumnIndex(DbContract.CityWeatherEntry.COLUMN_NAME_CITY_NAME)));
            cityWeather.setDegree(cursor.getString(cursor.getColumnIndex(DbContract.CityWeatherEntry.COLUMN_NAME_DEGREE)));
            cityWeatherList.add(cityWeather);
        }
        cursor.close();
        readableDatabase.close();
        return cityWeatherList;
    }

    public void truncateTable() {
        SQLiteDatabase writableDatabase = this.getWritableDatabase();
        writableDatabase.execSQL("delete from "+ DbContract.CityWeatherEntry.TABLE_NAME);
        writableDatabase.close();
    }
}
