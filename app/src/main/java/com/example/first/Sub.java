package com.example.first;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Sub extends AppCompatActivity {

    RecyclerView recyclerView2;
    Main main = new Main(); // Main 클래스의 arraylist 불러옴
    ArrayList<Item> itemArrayList;
    SubAdapter adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub);

        ImageView imgbt = findViewById(R.id.imgbt);

        imgbt.setOnClickListener(v -> {
            Intent intent = new Intent(Sub.this, Main.class);
            startActivity(intent);
        });

        recyclerList2();
    }

    public void recyclerList2(){
        // recycler view
        recyclerView2 = findViewById(R.id.recyclerView2);
        // List item 생성
        //itemArrayList = new ArrayList<Item>();
        itemArrayList = main.itemArrayList;

        // adapter 추가 및 layout manager 추가
        adapter2 = new SubAdapter(itemArrayList);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setAdapter(adapter2);
    }

}
