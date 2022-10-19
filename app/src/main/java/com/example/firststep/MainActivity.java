package com.example.firststep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 등록 -> 문제 등록
        Button imageButton = (Button) findViewById(R.id.등록);
        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), QuestionChange.class);
                startActivity(intent);
            }
        });

        // 학습 및 문제 -> 학습 및 문제 풀기
        Button imageButton2 = (Button) findViewById(R.id.학습및문제);
        imageButton2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Second_selection.class);
                startActivity(intent);
            }
        });

        // 문제 결과 -> 점수 보러가기
        Button imageButton3 = (Button) findViewById(R.id.문제결과);
        imageButton3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Score.class);
                startActivity(intent);
            }
        });


    }
}