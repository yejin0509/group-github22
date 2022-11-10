package com.example.firststep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class QuestionChange extends AppCompatActivity implements QuestionChangeAdapter.OnItemClickListener {

    DBSuppormer dbSuppormer;
    RecyclerView recyclerView;
    ArrayList<String> categoryList;
    QuestionChangeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_change);

        dbSuppormer = new DBSuppormer(QuestionChange.this);

        //뒤로가기 버튼
        ImageView backButton = (ImageView) findViewById(R.id.imageView2);
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        ImageView imageView3 = findViewById(R.id.imageView3);
        imageView3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), Question_input.class);
                startActivity(intent);
            }
        });

        // 삭제버튼
        ImageView deleteButton = (ImageView) findViewById(R.id.imageView);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        recyclerList();
        adapter.setOnItemClickListener(this);
    }

    // 리사이클러뷰, 카드뷰 사용
    public void recyclerList(){
        // recycler view
        recyclerView = findViewById(R.id.recyclerView);
        // List item 생성
        categoryList = new ArrayList<String>();

        List list = dbSuppormer.getResultCategoryN();

        for(int i = 0; i<list.size(); i++){
            categoryList.add(String.valueOf(list.get(i)));
        }

        // adapter 추가 및 layout manager 추가
        adapter = new QuestionChangeAdapter(this, categoryList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


    public void onItemClicked(View view, int position, String category) {
        Intent intent = new Intent(getApplicationContext(),Question_input_2.class);
        intent.putExtra("해당 카테고리 이름",category);
        startActivity(intent);
    }

}