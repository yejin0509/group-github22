package com.example.firststep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Class_list extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_list);

        Button Button1 = (Button) findViewById(R.id.button4);
        Button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Activity_study_1.class);
                startActivity(intent);
            }
        });

        Button Button2 = (Button) findViewById(R.id.button2);
        Button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Question.class);
                startActivity(intent);
            }
        });
    }
}