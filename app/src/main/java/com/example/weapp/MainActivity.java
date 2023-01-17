package com.example.weapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView degreeStatus, conditionsStatusText;
    private TextInputEditText cityName;
    private ImageView conditionsStatusImage, searchIcon, backIcon, forwardIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        degreeStatus = findViewById(R.id.degreeStatusID);
        conditionsStatusText = findViewById(R.id.conditionsStatusTextID);
        cityName = findViewById(R.id.textInputEditTextID);
        conditionsStatusImage = findViewById(R.id.conditionsStatusImageID);
        searchIcon = findViewById(R.id.searchButtonID);
        backIcon = findViewById(R.id.backArrowID);
        forwardIcon = findViewById(R.id.forwardArrowID);

        {
            backIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent yesterday = new Intent(MainActivity.this, yesterday_activity.class);
                    startActivity(yesterday);
                }
            });
            forwardIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent tomorrow = new Intent(MainActivity.this, tomorrow_activity.class);
                    startActivity(tomorrow);
                }
            });
        }
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String city = cityName.getText().toString();


            }
        });

    }


    private void collectDataFromAPI(String cityNameAPI) {
        String urlAPI = "http://api.weatherapi.com/v1/current.json?key=ca6f6cd999ed4563bf2181624231701&q="+ cityNameAPI +"&aqi=no";
        cityName.setText(cityNameAPI);
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlAPI, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String temp = response.getJSONObject("current").getString("temp_c");
                    degreeStatus.setText(temp + "°C");
                    String cond = response.getJSONObject("current").getJSONObject("condition").getString("text");
                    String condIcon = response.getJSONObject("current").getJSONObject("condition").getString("icon");
                    Picasso.get().load("http:".concat(condIcon)).into(conditionsStatusImage);
                    conditionsStatusText.setText(cond);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Enter valid city name", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
}