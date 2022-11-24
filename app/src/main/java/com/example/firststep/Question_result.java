package com.example.firststep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Question_result extends AppCompatActivity {
    DBSuppormer dbSuppormer;
    DBUser dbUser;
    ArrayList<UserClass> questionList = new ArrayList<UserClass>();
    List ImageList = new ArrayList<>();




    ArrayList<String> numList = new ArrayList<String>();
    int test = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_result);
        dbUser = new DBUser(Question_result.this);
        dbSuppormer = new DBSuppormer(Question_result.this);


        Intent intent = getIntent();
        String getCategoryName = intent.getStringExtra("CategoryName");
        String getDate = intent.getStringExtra("date");
        Log.i("카테고리 이름",getCategoryName);
        Log.i("날짜",getDate);

        Button Button = (Button) findViewById(R.id.clearbutton);
        Button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });




        questionList = dbUser.getResultList(getCategoryName, getDate);
        ImageList = dbSuppormer.getImageList(getCategoryName);

        for(int i = 0; i < questionList.size(); i++){
            numList.add(String.valueOf(i));
        }


        PrintValues();

    }

    private void PrintValues() {

        TextView Number = (TextView) findViewById(R.id.number);
        Number.setText(questionList.get(Integer.parseInt(numList.get(test))).getQNumber());
        Log.i("날짜-넘버", questionList.get(Integer.parseInt(numList.get(test))).getQNumber());

        String answer = questionList.get(Integer.parseInt(numList.get(test))).getResultA();
        String user_answer = questionList.get(Integer.parseInt(numList.get(test))).getUserA();

        for(int i = 0; i < ImageList.size(); ++i){
            if (ImageList.get(i).equals(answer)) {
                ImageView image = (ImageView) findViewById(R.id.imageView1);
                image.setImageBitmap((Bitmap) ImageList.get(1));
            }
        }
        String ch1 = questionList.get(Integer.parseInt(numList.get(test))).getChoice1();
        String ch2 = questionList.get(Integer.parseInt(numList.get(test))).getChoice2();
        String ch3 = questionList.get(Integer.parseInt(numList.get(test))).getChoice3();
        TextView q1=(TextView)findViewById(R.id.tv_Q1);
        TextView q2=(TextView)findViewById(R.id.tv_Q2);
        TextView q3=(TextView)findViewById(R.id.tv_Q3);

        TextView choice1 = (TextView) findViewById(R.id.choice1);
        choice1.setText(questionList.get(Integer.parseInt(numList.get(test))).getChoice1());
        TextView choice2 = (TextView) findViewById(R.id.choice2);
        choice2.setText(questionList.get(Integer.parseInt(numList.get(test))).getChoice2());
        TextView choice3 = (TextView) findViewById(R.id.choice3);
        choice3.setText(questionList.get(Integer.parseInt(numList.get(test))).getChoice3());


        //o x 이미지 및 next 버튼
        ImageView wrong = (ImageView)findViewById(R.id.qResultWrong2);
        ImageView correct = (ImageView)findViewById(R.id.qResultCorrect2);
        //ox 이미지 및 next 버튼 숨기기
        wrong.setVisibility(View.INVISIBLE);
        correct.setVisibility(View.INVISIBLE);


        if(answer.equals(user_answer)){
            correct.setVisibility(View.VISIBLE);
            wrong.setVisibility(View.INVISIBLE);

            if(ch1.equals(answer)){
                choice1.setTextColor(Color.parseColor("#568A35"));
                q1.setTextColor(Color.parseColor("#568A35"));
            }
            else if(ch2.equals(answer)){
                choice2.setTextColor(Color.parseColor("#568A35"));
                q2.setTextColor(Color.parseColor("#568A35"));
            }
            else{
                choice3.setTextColor(Color.parseColor("#568A35"));
                q3.setTextColor(Color.parseColor("#568A35"));
            }
        }
        else{
                correct.setVisibility(View.INVISIBLE);
                wrong.setVisibility(View.VISIBLE);

                if(ch1.equals(answer)){
                    choice1.setTextColor(Color.parseColor("#568A35"));
                    q1.setTextColor(Color.parseColor("#568A35"));
                    if(user_answer.equals(ch2)){
                        choice2.setTextColor(Color.parseColor("#ff0000"));
                        q2.setTextColor(Color.parseColor("#ff0000"));
                    }
                    else{
                        choice3.setTextColor(Color.parseColor("#ff0000"));
                        q3.setTextColor(Color.parseColor("#ff0000"));
                    }
                }
                else if(ch2.equals(answer)){
                    choice2.setTextColor(Color.parseColor("#568A35"));
                    q2.setTextColor(Color.parseColor("#568A35"));
                    if(user_answer.equals(ch1)){
                        choice1.setTextColor(Color.parseColor("#ff0000"));
                        q1.setTextColor(Color.parseColor("#ff0000"));
                    }
                    else{
                        choice3.setTextColor(Color.parseColor("#ff0000"));
                        q3.setTextColor(Color.parseColor("#ff0000"));
                    }
                }
                else{
                    choice3.setTextColor(Color.parseColor("#568A35"));
                    q3.setTextColor(Color.parseColor("#568A35"));
                    if(user_answer.equals(ch2)){
                        choice2.setTextColor(Color.parseColor("#ff0000"));
                        q2.setTextColor(Color.parseColor("#ff0000"));
                    }
                    else{
                        choice1.setTextColor(Color.parseColor("#ff0000"));
                        q1.setTextColor(Color.parseColor("#ff0000"));
                    }
                }
        }


       /*

        test++;


        List value_1 = dbSuppormer.getUserList(user_answer);
        int id = (int)value_1.get(0);
        while ((int)value_1.get(0) == id){
            value_1 = dbSuppormer.getValue(categoryname);
        }*/
    }
}

