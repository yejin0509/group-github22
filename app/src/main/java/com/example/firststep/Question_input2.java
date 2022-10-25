package com.example.firststep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Question_input2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_input2);

        EditText answer = findViewById(R.id.edit_answer); // 카테고리 edit
        ImageView img = findViewById(R.id.img); // 이미지 버튼
        TextView num = findViewById(R.id.num); // 숫자

        //LayoutInflater 객체 생성
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.question_input, null);

        ImageView plus2 = view.findViewById(R.id.plus);

        plus2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d("answer  ", answer.getText().toString());
            }
        });

    }
}