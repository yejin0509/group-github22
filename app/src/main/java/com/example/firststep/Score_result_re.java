package com.example.firststep;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Score_result_re extends AppCompatActivity implements Score_result_re_2.OnItemClickListener {

    DBSuppormer dbSuppormer;
    DBUser dbUser;
    RecyclerView recyclerView;

    ArrayList<UserClass> scoreList = new ArrayList<UserClass>();     //결과 리스트
    ArrayList<SuppormerClass> ImageList = new ArrayList<SuppormerClass>();    //이미지 리스트

    ArrayList<String> numList = new ArrayList<String>();
    int test = 0;

    Score_result_re_2 adapter1;
    String getCategoryName, getDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_result_re);

        dbSuppormer = new DBSuppormer(Score_result_re.this);
        dbUser = new DBUser(Score_result_re.this);

        //결과로 돌아가기 버튼
        Button mainButton = (Button) findViewById(R.id.clearbutton);
        mainButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Score.class);
                startActivity(intent);
            }
        });

        //문제 풀이에 대한 결과 가져옴
        Intent intent = getIntent();

        getCategoryName = intent.getStringExtra("CategoryName");
        getDate = intent.getStringExtra("date");
        Log.i("가져온 네임", getCategoryName);
        Log.i("가져온 날짜", getDate);

        recyclerList();



    }

    @SuppressLint("LongLogTag")
    private void recyclerList() {
        // recycler view
        recyclerView = findViewById(R.id.recyclerView);

        //결과와 이미지를 불러옴
        scoreList = dbUser.getResultList(getCategoryName,getDate);
        Log.i("가져온 questionList", String.valueOf(scoreList));
        ImageList = dbSuppormer.getResult(getCategoryName);

        for(int i = 0; i < scoreList.size(); i++){
            numList.add(String.valueOf(i));
        }

        // adapter 추가 및 layout manager 추가
        adapter1 = new Score_result_re_2(this, scoreList, ImageList, numList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter1);

        TextView title = (TextView) findViewById(R.id.score_title);
        title.setText(getCategoryName);

    }


    @Override
    public void onItemClick(View view, int position, String category) {
        Intent intent = new Intent(getApplicationContext(),Score.class);
        intent.putExtra("해당 카테고리 이름",category);
        startActivity(intent);
    }
}