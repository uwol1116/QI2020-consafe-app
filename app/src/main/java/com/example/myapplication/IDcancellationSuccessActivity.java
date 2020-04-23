package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IDcancellationSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idcancellation_success);

        Button btn_bk_2 = findViewById(R.id.btn_bk_2);

        btn_bk_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IDcancellationSuccessActivity.this, LoginActivity.class);
                startActivity(intent);

                MainActivity MA = (MainActivity) MainActivity.activity;
                MA.finish();

                finish();
            }
        });
    }
}
