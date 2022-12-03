package com.example.firststep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Activity_study_1 extends AppCompatActivity {

    DBSuppormer dbSuppormer;
    Intent intent;
    String categoryname;
    ArrayList<SuppormerClass> itemArraylist;

    int n = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_1);

        dbSuppormer = new DBSuppormer(Activity_study_1.this);
        itemArraylist = new ArrayList<SuppormerClass>();


        intent = getIntent();
        categoryname = intent.getStringExtra("해당 카테고리 이름");


        TextView textView3 = (TextView) findViewById(R.id.textView3);
        ImageView imageView2 = (ImageView) findViewById(R.id.imageView2);
        TextView editTextNumber = (TextView) findViewById(R.id.editTextNumber);
        Button backStudy = findViewById(R.id.button8);

        itemArraylist = dbSuppormer.getResult(categoryname);

        backStudy.setVisibility(View.GONE);


        textView3.setText(categoryname);
        editTextNumber.setText(itemArraylist.get(n).getAnswer());
        imageView2.setImageBitmap(itemArraylist.get(n).getImage());

        Log.i("답변", String.valueOf(n)+ itemArraylist.get(n).getAnswer());

        if(n == 0){

        } else {

        }


        Button Button2 = (Button) findViewById(R.id.button2);
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Class_list.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        // 문제학습 문제 슬라이드 부분
        Button nextStudy = findViewById(R.id.button4);
        nextStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                n += 1;
                backStudy.setVisibility(View.VISIBLE);

                if(n < itemArraylist.size()){
                    Log.i("답변", String.valueOf(n)+ itemArraylist.get(n).getAnswer());
                    editTextNumber.setText(itemArraylist.get(n).getAnswer());
                    imageView2.setImageBitmap(itemArraylist.get(n).getImage());
                } else {
                    Log.i("문제끝", String.valueOf(n));
                    Intent intent = new Intent(getApplicationContext(), QuestionLast.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });


        backStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                n -= 1;

                if(n < itemArraylist.size() && n > 0){
                    Log.i("답변", String.valueOf(n)+ itemArraylist.get(n).getAnswer());
                    editTextNumber.setText(itemArraylist.get(n).getAnswer());
                    imageView2.setImageBitmap(itemArraylist.get(n).getImage());
                } else if(n == 0){
                    backStudy.setVisibility(View.GONE);
                    Log.i("답변", String.valueOf(n)+ itemArraylist.get(n).getAnswer());
                    editTextNumber.setText(itemArraylist.get(n).getAnswer());
                    imageView2.setImageBitmap(itemArraylist.get(n).getImage());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                }
            }
        });

    }
}