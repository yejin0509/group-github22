package com.example.firststep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Class_list extends AppCompatActivity implements Class_listAdapter.OnItemClickListener {

    DBSuppormer dbSuppormer;
    RecyclerView recyclerView;
    ArrayList<String> categoryList;
    Class_listAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_list);

        ImageView backButton = (ImageView) findViewById(R.id.imageView2);
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Second_selection.class);
                startActivity(intent);
            }
        });

        dbSuppormer = new DBSuppormer(Class_list.this);

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
        adapter = new Class_listAdapter(this, categoryList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


    public void onItemClicked(View view, int position, String category) {
        Intent intent = new Intent(getApplicationContext(),Activity_study_1.class);
        intent.putExtra("해당 카테고리 이름",category);
        startActivity(intent);
    }

}

