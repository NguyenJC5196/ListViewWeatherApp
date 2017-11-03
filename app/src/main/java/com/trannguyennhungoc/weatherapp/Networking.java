package com.trannguyennhungoc.weatherapp;

import android.content.Context;
import android.net.Uri;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.stream.JsonReader;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thuyb on 18-Sep-17.
 */

public class Networking {
    private static final String WEATHER_BASE_URL = "api.openweathermap.org";/*?id=2172797&appid=d3af81ee5cd5fe36cd0d8be7b1287bb1";*/

    private static final String TAG = "Networking";
    private static final String APPID = "d3af81ee5cd5fe36cd0d8be7b1287bb1";
    private int CITY_ID = 1566083;
    private static final String UNIT_CELSIUS = "metric";
    private static final String UNIT_FAHRENHEIT = "imperial";

    private static final String WEATHER_ICON_BASE_URL = "openweathermap.org";/*"10d.png;";*/



    private Context _contextParent;


    public void setCITY_ID(int CityID) {
        this.CITY_ID = CityID;
    }

    String buildUrl(int cityID, String conditionDegree) throws MalformedURLException {
        Uri.Builder builder = new Uri.Builder()
                .scheme("http")
                .authority(WEATHER_BASE_URL)
                .appendPath("data")
                .appendPath("2.5")
                .appendPath("weather")
                .appendQueryParameter("id", String.valueOf(cityID))
                .appendQueryParameter("appid", APPID);
        if (conditionDegree.matches("CEL")) {
            builder.appendQueryParameter("units", UNIT_CELSIUS);
        } else if (conditionDegree.matches("FAH")) {
                builder.appendQueryParameter("units", UNIT_FAHRENHEIT);
        }
        return builder.build().toString();
    }
    Uri buildUrlIcon(String key) throws MalformedURLException {
        Uri builder = new Uri.Builder()
                .scheme("http")
                .authority(WEATHER_ICON_BASE_URL)
                .appendPath("img")
                .appendPath("w")
                .appendPath(key + ".png")
                .build();

        return builder;
    }

    public List<City> getCityRes(Context context) throws IOException {
        InputStream in = context.getAssets().open("city.json");
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readCitiesArray(reader);
        } finally {
            reader.close();
        }
    }

    private List<City> readCitiesArray(JsonReader reader) throws IOException {
        List<City> cities = new ArrayList<City>();
        City cityTemp ;
        reader.beginArray();
        int count = 0;
        while (reader.hasNext()) {
             cities.add(readCity(reader));
        }
        reader.endArray();
        return cities;
    }

    private boolean isMatchCountry(City city, String conditionCountry){
        return city.get_Country().matches(conditionCountry);
    }
    /*private String (JsonReader reader) throws IOException {
        String result;
        reader.beginObject();
        String name
        while (reader.hasNext()){

        }
        return result;
    }*/
    @Nullable
    private City readCity(JsonReader reader) throws IOException {
        City city = new City();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id")) {
                city.set_Id(reader.nextInt());
            } else if (name.equals("name")) {
                city.set_Name(reader.nextString());
            } else if (name.equals("country")) {
                city.set_Country(reader.nextString());
            } else if (name.equals("coord")) {
                city.set_Coord(readCoord(reader));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return city;
    }

    private City.Coord readCoord(JsonReader reader) throws IOException {
//        City.Coord coord = null;
        double lat = 0;
        double lon = 0;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("lon")) {
                lon = reader.nextDouble();
            } else if (name.equals("lat")) {
                lat = reader.nextDouble();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new City.Coord(lon, lat);
    }


}

