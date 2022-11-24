package com.example.firststep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Score extends AppCompatActivity {

    private DBUser dbUser;
    private RecyclerView recyclerView;
    private ArrayList<ScoreResult> scoreL;
    private ArrayList<String> countL;
    private Score_ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score);

        //결과보기 버튼
//        Button imageButton = (Button) findViewById(R.id.button4);
//        imageButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), History_result.class);
//                startActivity(intent);
//            }
//        });

        //뒤로가기 버튼
        ImageView backButton = (ImageView) findViewById(R.id.imageView2);
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        setInit();
//        recyclerList();
    }

//    @SuppressLint("LongLogTag")
//    private void recyclerList() {
//        // recycler view
//        recyclerView = findViewById(R.id.history_score);
//        // List item 생성
//        categoryL = new ArrayList<String>();
//
//        List list = dbUser.getResultCategoryN();
//
//        for (int i = 0; i < list.size(); i++) {
//            categoryL.add(String.valueOf(list.get(i)));
//        }
//
//        // adapter 추가 및 layout manager 추가
//        adapter = new Score_ListAdapter( this,categoryL);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);
//
//
//    }
    //기본틀
    private void setInit(){
        dbUser=new DBUser(this);
        recyclerView=findViewById(R.id.history_score);
        scoreL=new ArrayList<>();
        countL=new ArrayList<>();

        loadRecentDB();

        recyclerView.smoothScrollToPosition(0);
    }
//    저장된 db 가져오기
    private void loadRecentDB(){
        scoreL=dbUser.getResult();
        List list=dbUser.getCount();
    //        Log.i("확인:", String.valueOf(list));
        for(int i=list.size()-1;i>=0;i--){
            countL.add(String.valueOf(list.get(i)));
        }

        if(adapter==null){
            adapter=new Score_ListAdapter(scoreL,countL, this);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
        }
    }

//     리사이클러뷰, 카드뷰 사용
//    public void recyclerList(){
//        // recycler view
//        recyclerView = findViewById(R.id.history_score);
//        // List item 생성
//        hList = new ArrayList<String>();
//
//        List list = dbUser.getResult();
//
//        for(int i = 0; i<list.size(); i++){
//            hList.add(String.valueOf(list.get(i)));
//        }
//
//
//
//        // adapter 추가 및 layout manager 추가
//        adapter = new Class_listAdapter(this, hList);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);
//    }
}