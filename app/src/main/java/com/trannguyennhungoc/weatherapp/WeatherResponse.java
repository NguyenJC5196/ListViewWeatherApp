package com.trannguyennhungoc.weatherapp;

import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by thuyb on 18-Sep-17.
 */

public class WeatherResponse {
    private String _Date;
    private String _Place;
    private double _Temp;
    private String _Description;
    private String _Icon;

    public String get_Date() {
        return _Date;
    }

    public void set_Date(String _Date) {
        this._Date = _Date;
    }

    public String get_Place() {
        return _Place;
    }

    public void set_Place(String _Place) {
        this._Place = _Place;
    }

    public double get_Temp() {
        return _Temp;
    }

    public void set_Temp(double _Temp) {
        this._Temp = _Temp;
    }

    public String get_Description() {
        return _Description;
    }

    public void set_Description(String _Description) {
        this._Description = _Description;
    }

    public String get_Icon() {
        return _Icon;
    }

    public void set_Icon(String _Icon) {
        this._Icon = _Icon;
    }

    public WeatherResponse(String _Date, String _Place, double _Temp, String _Description, String icon) {
        this._Date = _Date;
        this._Place = _Place;
        this._Temp = _Temp;
        this._Description = _Description;
        this._Icon = icon;
    }

    public WeatherResponse(){
        this._Icon = null;
        this._Date = null;
        this._Place = null;
        this._Temp = 0;
        this._Description = null;
        this._Icon = null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this._Date + "\n")
                .append(this._Place + "\n")
                .append(this._Temp + "\n")
                .append(this._Description + "\n")
                .append(this._Icon + "\n");
        return builder.toString();
    }

    public WeatherResponse readFromJson(JSONObject object) throws JSONException {
        WeatherResponse response;
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder builderPlace = new StringBuilder(object.getString("name"));
        builderPlace.append(", ");

        JSONObject object1 = object.getJSONObject("main");
        JSONObject object2 = object.getJSONObject("sys");
        JSONArray array = object.getJSONArray("weather");

        builderPlace.append(object2.getString("country"));

        response = new WeatherResponse(
                dateFormat.format(Calendar.getInstance().getTime()),
                builderPlace.toString(),
                object1.getDouble("temp"),
                array.getJSONObject(0).getString("description"),
                array.getJSONObject(0).getString("icon")
        );
        return response;
    }
}
