package com.example.weapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

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

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent yesterday = new Intent(MainActivity.this,yesterday_activity.class);
                startActivity(yesterday);
            }
        });
        forwardIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tomorrow = new Intent(MainActivity.this,tomorrow_activity.class);
                startActivity(tomorrow);
            }
        });
    }
}