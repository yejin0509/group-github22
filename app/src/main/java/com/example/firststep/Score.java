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

public class Score extends AppCompatActivity implements Score_ListAdapter.OnItemClickListener {

    private DBUser dbUser;
    private RecyclerView recyclerView;
    private ArrayList<ScoreResult> scoreL;
    private ArrayList<String> countL;
    private Score_ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score);

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
        adapter.setOnItemClickListener(this);
    }

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
        for(int i=list.size()-1;i>=0;i--){
            countL.add(String.valueOf(list.get(i)));
        }


            adapter=new Score_ListAdapter(scoreL,countL,this );
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);

    }
    @Override
    public void onItemClick(View view, int position, String category, String date2) {
        Intent intent = new Intent(getApplicationContext(),Score_result_re.class);
        intent.putExtra("CategoryName",category);
        intent.putExtra("date",date2);
        startActivity(intent);
    }

}