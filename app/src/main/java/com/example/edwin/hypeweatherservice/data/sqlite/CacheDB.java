package com.example.edwin.hypeweatherservice.data.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by edwin on 19-5-2016.
 */
public class CacheDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "WeatherCache";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_WEATHER = "weather";
    private static final String COL_WEATHER_ID = "id";
    private static final String COL_WEATHER_DESC = "description";

    public CacheDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;
        sql = "CREATE TABLE "+ TABLE_WEATHER + " (" +
                COL_WEATHER_ID + "INTEGER, " +
                COL_WEATHER_DESC + " TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS weather");
            onCreate(db);
        }
    }

    public void addWeather(Weather weather) {
        ContentValues values;
        values = new ContentValues();
        values.put(COL_WEATHER_ID, weather.getId());
        values.put(COL_WEATHER_DESC, weather.getDescription());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_WEATHER, null, values);
        db.close();
    }

    public void getWeather(int id) {

    }

}
