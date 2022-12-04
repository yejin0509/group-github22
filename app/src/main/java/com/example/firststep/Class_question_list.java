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

public class Class_question_list extends AppCompatActivity implements Class_question_listAdapter.OnItemClickListener {

    DBSuppormer dbSuppormer;
    RecyclerView recyclerView;
    ArrayList<String> categoryList;
    ArrayList<String> answerList;
    ArrayList<String> anotherList;
    Class_question_listAdapter adapter1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_question_list);

        dbSuppormer = new DBSuppormer(Class_question_list.this);

        ImageView backButton = (ImageView) findViewById(R.id.imageView2);
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Second_selection.class);
                startActivity(intent);
            }
        });



        recyclerList();

        adapter1.setOnItemClickListener(this);


    }

    @SuppressLint("LongLogTag")
    private void recyclerList() {
        // recycler view
        recyclerView = findViewById(R.id.recyclerView);
        // List item 생성
        categoryList = new ArrayList<String>();
        // List - 정답 item 생성
        answerList = new ArrayList<String>();

        List list = dbSuppormer.getResultCategoryN();
        List answer = dbSuppormer.getResultCategoryAnswer();
        List<String> list1 = new ArrayList<>();
        //정답 외에 문제 선택지 List 생성
        List<String> answer_another = new ArrayList<>();
        //랜덤
        Random random = new Random();


        for (int i = 0; i < list.size(); i++) {
            categoryList.add(String.valueOf(list.get(i)));
            list1.add(String.valueOf(list.get(i)));
        }




        for(int i = 0; i< answer.size(); i++) {
            answerList.add(String.valueOf(answer.get(i)));
        }



        //랜덤으로 정답 외에 선택지 List로 저장.
        for(int i = 0; i< answer.size(); i++) {
            String string_answer = String.valueOf(answer.get(i));       //string_answer 정답
            Log.i("string_answer", string_answer);
            answer_another.clear();
            //중복 방지
            String another_1 = string_answer;

            for (int j = 0; j < answer.size(); j++) {
                int randomIndex = random.nextInt(answer.size());        //random index
                String string_another = String.valueOf(answer.get(randomIndex));
                Log.i("string_another", string_another);

                //정답과 another이 다르면서 저장된 another과 다르면 저장
                if (!string_answer.equals(string_another) && !another_1.equals(string_another)) {
                    answer_another.add(string_another);
                    another_1 = string_another;
                    //2개만 받고 break;
                    if(answer_another.size() == 2){
                        Log.i("answer_another", String.valueOf(answer_another.get(0)));
                        Log.i("answer_another", String.valueOf(answer_another.get(1)));
                        break;
                    }
                }
            }
        }

        // adapter 추가 및 layout manager 추가
        adapter1 = new Class_question_listAdapter(this, categoryList);
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