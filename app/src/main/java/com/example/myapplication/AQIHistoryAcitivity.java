package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AQIHistoryAcitivity extends AppCompatActivity {

    Button btn_goco, btn_gosotwo, btn_gonotwo, btn_goothree, btn_gopmtwofive;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aqihistory_acitivity);

        btn_goco = findViewById(R.id.btn_goco);
        btn_gosotwo = findViewById(R.id.btn_gosotwo);
        btn_gonotwo = findViewById(R.id.btn_gonotwo);
        btn_goothree = findViewById(R.id.btn_goothree);
        btn_gopmtwofive = findViewById(R.id.btn_gopmtwofive);

        btn_gosotwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AQIHistoryAcitivity.this, SO2HistoryActivity.class);
                startActivity(intent);
            }
        });

        btn_goco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AQIHistoryAcitivity.this, COHistoryActivity.class);
                startActivity(intent1);
            }
        });

        btn_goothree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(AQIHistoryAcitivity.this, O3HistoryActivity.class);
                startActivity(intent2);
            }
        });

        btn_gonotwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(AQIHistoryAcitivity.this, NO2HistoryActivity.class);
                startActivity(intent3);
            }
        });

        btn_gonotwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(AQIHistoryAcitivity.this, NO2HistoryActivity.class);
                startActivity(intent4);
            }
        });

        btn_gopmtwofive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(AQIHistoryAcitivity.this, PM25HistoryActivity.class);
                startActivity(intent5);
            }
        });



    }
}
