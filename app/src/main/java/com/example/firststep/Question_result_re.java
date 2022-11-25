package com.example.firststep;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class Question_result_re extends AppCompatActivity implements Question_result_re_2.OnItemClickListener {

    DBSuppormer dbSuppormer;
    DBUser dbUser;
    RecyclerView recyclerView;

    ArrayList<UserClass> questionList = new ArrayList<UserClass>();     //결과 리스트
    ArrayList<SuppormerClass> ImageList = new ArrayList<SuppormerClass>();    //이미지 리스트

    ArrayList<String> numList = new ArrayList<String>();
    int test = 0;

    Question_result_re_2 adapter1;
    String getCategoryName, getDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_result_re);

        dbSuppormer = new DBSuppormer(Question_result_re.this);
        dbUser = new DBUser(Question_result_re.this);

        //완료 버튼
        Button mainButton = (Button) findViewById(R.id.clearbutton);
        mainButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Class_question_list.class);
                startActivity(intent);
            }
        });

        //문제 풀이에 대한 결과 가져옴
        Intent intent = getIntent();

        getCategoryName = intent.getStringExtra("CategoryName");
        getDate = intent.getStringExtra("date");

        recyclerList();



    }

    @SuppressLint("LongLogTag")
    private void recyclerList() {
        // recycler view
        recyclerView = findViewById(R.id.recyclerView);

        //결과와 이미지를 불러옴
        questionList = dbUser.getResultList(getCategoryName, getDate);
        ImageList = dbSuppormer.getResult(getCategoryName);

        for(int i = 0; i < questionList.size(); i++){
            numList.add(String.valueOf(i));
        }

        // adapter 추가 및 layout manager 추가
        adapter1 = new Question_result_re_2(this, questionList, ImageList, numList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter1);


    }


    @Override
    public void onItemClick(View view, int position, String category) {
        Intent intent = new Intent(getApplicationContext(),Question.class);
        intent.putExtra("해당 카테고리 이름",category);
        startActivity(intent);
    }
}