package com.onlineaavedan.capturephotos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class CaptureActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        button = findViewById(R.id.bnt_start);
        button.setOnClickListener(View -> {
            startActivity(new Intent(getApplicationContext(), NextActivity.class));
        });
    }
}