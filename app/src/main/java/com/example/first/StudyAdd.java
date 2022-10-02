package com.example.first;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class StudyAdd extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Item> itemArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_study);

        // 학습 메인으로 가기
        ImageView imgbt = findViewById(R.id.imgbt);

        imgbt.setOnClickListener(v -> {
            Intent intent = new Intent(StudyAdd.this, StudyMain.class);
            startActivity(intent);
        });


        recyclerView = findViewById(R.id.recyclerView);

        itemArrayList = new ArrayList<>();
        itemArrayList.add(new Item(1, "시계",R.drawable.clock));
        itemArrayList.add(new Item(2, "의자",R.drawable.chair));
        itemArrayList.add(new Item(3, "옷",R.drawable.shirt));

        AddAdapter adapter = new AddAdapter(itemArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);



    }
}