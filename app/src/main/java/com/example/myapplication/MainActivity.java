package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        @SuppressLint("WrongViewCast") Button moveButton = findViewById(R.id.imgbt2);
        moveButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(),Add_study.class);
            startActivity(intent);
        });
    }


}