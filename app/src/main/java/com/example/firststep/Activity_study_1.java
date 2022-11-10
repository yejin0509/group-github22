package com.example.firststep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_1);

        dbSuppormer = new DBSuppormer(Activity_study_1.this);

        intent = getIntent();
        categoryname = intent.getStringExtra("해당 카테고리 이름");
        List list = dbSuppormer.getResultList(categoryname);


        TextView textView3 = (TextView) findViewById(R.id.textView3);
        ImageView imageView2 = (ImageView) findViewById(R.id.imageView2);
        EditText editTextNumber = (EditText) findViewById(R.id.editTextNumber);


        textView3.setText(String.valueOf(list.get(1)));
        editTextNumber.setText(String.valueOf(list.get(3)));
        imageView2.setImageBitmap((Bitmap) list.get(2));

        Button Button2 = (Button) findViewById(R.id.button2);
        Button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Class_list.class);
                startActivity(intent);
            }
        });
    }
}