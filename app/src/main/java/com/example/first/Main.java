package com.example.first;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class Main extends AppCompatActivity {

    RecyclerView recyclerView;
    static ArrayList<Item> itemArrayList;
    MainAdapter adapter;
    EditText editText;
    String textedit;
    int num = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //LayoutInflater 객체 생성
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.main_sub, null);

        Button button = findViewById(R.id.button);
        Button button2 = findViewById(R.id.button2);
        EditText inputText = findViewById(R.id.inputText);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main.this, Sub.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textedit = inputText.getText().toString();

                Log.v("text",textedit);

                itemArrayList.add(new Item(num, textedit));

                num += 1;

                adapter.notifyDataSetChanged();
            }
        });

        recyclerList();
    }

    public void recyclerList(){
        // recycler view
        recyclerView = findViewById(R.id.recyclerView);
        // List item 생성
        itemArrayList = new ArrayList<Item>();

        // adapter 추가 및 layout manager 추가
        adapter = new MainAdapter(itemArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }



}
