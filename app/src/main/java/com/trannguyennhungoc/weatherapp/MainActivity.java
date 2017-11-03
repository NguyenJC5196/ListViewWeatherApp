package com.trannguyennhungoc.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//import butterknife.BindView;
//import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;


    private RecyclerView.Adapter adapter;
    private List<City> cityList;
    private List<WeatherResponse> weatherResponses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.rv_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        String conditionCountry = "VN";

//        ProgressBar progressBar = new ProgressBar(this);
//        progressBar.

        try {
            cityList = new Networking().getCityRes(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String Condition = "cel";
        ArrayList<String> idList = getIdMatched(cityList, conditionCountry);
        ArrayList<String> sUrl = null;
        try {
            sUrl = buildUrlArray(idList, Condition.toUpperCase());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        /*assert sUrl != null;
        for (String url : sUrl) {
            if (!url.isEmpty()) {
                weatherResponses.add(loadFromURL(url));
//                Log.i("THIS", "onCreate: " + url);
            }
        }*/
        weatherResponses = new ArrayList<>(sUrl.size());

        for (String url : sUrl) {
            StringRequest request = new StringRequest(Request.Method.GET,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject object = new JSONObject(response);
                                WeatherResponse weather = new WeatherResponse().readFromJson(object);
                                weatherResponses.add(weather);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);
        }


        adapter = new MyAdapter(weatherResponses, this);
        recyclerView.setAdapter(adapter);
//        weatherResponses.add(0,loadRecyclerView("http://api.openweathermap.org/data/2.5/weather?id=2172797&appid=d3af81ee5cd5fe36cd0d8be7b1287bb1"));
//            WeatherResponse response = loadRecyclerView("http://api.openweathermap.org/data/2.5/weather?id=2172797&appid=d3af81ee5cd5fe36cd0d8be7b1287bb1");


    }
    private ArrayList<String> buildUrlArray(ArrayList<String> idArray, String conditionDegree) throws MalformedURLException {
        ArrayList<String> result = new ArrayList<>(idArray.size());
        for (String s : idArray) {
            int i = Integer.parseInt(s);
            result.add(new Networking().buildUrl(i, conditionDegree.toUpperCase()));
//            Log.i("THIS", "buildUrlArray: " + resul + "\n");
        }
        return result;
    }

    /*private List<City> filterIdByName(List<City> cityList, String conditionCountry) {
        int guessSize = cityList.size();
        // This is to avoid @variable result's error point to null
        //
        List<City> result = new ArrayList<>(guessSize);
       *//* for (City city : cityList) {
            if (city.get_Country().matches(conditionCountry)) {
                Log.i("THIS", "filterIdByName: Name compare " + city.get_Country() + " and " + conditionCountry);
//                result.add(city);
                StringBuilder builder = new StringBuilder(city.toString() + "\n");
                Log.i("THIS", "filterIdByName: "+builder.toString());
                cityList.remove(city);
            }
            temp++;
        }*//*
        City city;
        for (int i = 0; i < cityList.size(); i++) {
            city = cityList.get(i);
            if (city.get_Country().matches(conditionCountry)) {
                result.add(i, city);
                StringBuilder builder = new StringBuilder(city.toString() + "\n" + result.contains(city));
                Log.i("THIS", "filterIdByName: String :" + builder.toString());
            }
        }
//        Log.i("THIS", "filterIdByName: loop runs" + bui);
//        result.addAll(cityList);

        return result;
    }*/


    private ArrayList<String> getIdMatched(List<City> cityList, String conditionString) {
        ArrayList<String> count = new ArrayList<>();
        for (City city : cityList) {
            if (city.get_Country().matches(conditionString)) {
                String sInt = String.valueOf(city.get_Id());
                count.add(sInt);
            }
        }
        return count;
    }

    /*private WeatherResponse loadFromURL(ArrayList<String> url) {
        final WeatherResponse[] weatherResponse = new WeatherResponse[1];
//        weatherResponse.set_Date(Calendar.getInstance().getTime());

//        ArrayList<WeatherResponse> responseArrayList;
        final Date date = Calendar.getInstance().getTime();
        final double[] temp = {0.0};
        final String[] place = {null};
        final String[] description = {null};
        final String[] icon = {null};

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            StringBuilder builderPlace = new StringBuilder(object.getString("name"));

                            JSONObject object1 = object.getJSONObject("main");
                            JSONObject object2 = object.getJSONObject("sys");
                            JSONArray array = object.getJSONArray("weather");

                            builderPlace.append(object2.getString("country"));

                            weatherResponse[0] = new WeatherResponse(
                                    date,
                                    builderPlace.toString(),
                                    object1.getDouble("temp"),
                                    array.getJSONObject(0).getString("description"),
                                    array.getJSONObject(0).getString("icon"));
//                            weatherResponses.add(weatherResponse);

//                            weatherResponse.set_Temp(object1.getDouble("temp"));
//                            temp[0] = object1.getDouble("temp");

//                            weatherResponse.set_Place(builderPlace.toString());
//                            place[0] = builderPlace.toString();
//                            weatherResponse.set_Description(array.getJSONObject(0).getString("description"));
//                            description[0] = array.getJSONObject(0).getString("description");
//                            weatherResponse.set_Icon(array.getJSONObject(0).getString("icon"));
//                            icon[0] = array.getJSONObject(0).getString("icon");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
        return weatherResponse[0];
//    }*/
}
