package com.example.weapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
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

public class tomorrow_activity extends AppCompatActivity {

    private TextView degreeStatus, conditionsStatusText;
    private TextInputEditText cityNameEdit;
    private ImageView conditionsStatusImage, searchIcon, backIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tomorrow);

        degreeStatus = findViewById(R.id.degreeStatusID);
        conditionsStatusText = findViewById(R.id.conditionsStatusTextID);
        cityNameEdit = findViewById(R.id.textInputEditTextID);
        conditionsStatusImage = findViewById(R.id.conditionsStatusImageID);
        searchIcon = findViewById(R.id.searchButtonID);
        backIcon = findViewById(R.id.backArrowID);

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent yesterday = new Intent(tomorrow_activity.this,MainActivity.class);
                startActivity(yesterday);
            }
        });
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String city = cityNameEdit.getText().toString();
                if(city.isEmpty()){
                    Toast.makeText(tomorrow_activity.this, "Please enter city name", Toast.LENGTH_SHORT).show();
                }
                else{
                    collectDataFromAPI(city);
                }
            }

        });
    }

    private void collectDataFromAPI(String cityNameAPI) {
        String url = "http://api.weatherapi.com/v1/forecast.json?key=090a784c78ff437d89f220953231701&q=" + cityNameAPI + "&days=1&aqi=no&alerts=no";
        cityNameEdit.setText(cityNameAPI);
        RequestQueue requestQueue = Volley.newRequestQueue(tomorrow_activity.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String temp = response.getJSONObject("current").getString("temp_c");
                    degreeStatus.setText(temp);
                    String cond = response.getJSONObject("current").getJSONObject("condition").getString("text");
                    conditionsStatusText.setText(cond);
                    String condIcon = response.getJSONObject("current").getJSONObject("condition").getString("icon");
                    Picasso.get().load("http:".concat(condIcon)).into(conditionsStatusImage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(tomorrow_activity.this, "Enter valid city name", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}