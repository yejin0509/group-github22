package com.example.firststep;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Question_input extends AppCompatActivity {

    static Bitmap imgBit;
    ImageView edit_img;
    EditText edit_answer;
    TextView edit_num;

    EditText edit_categoryN;

    DBSuppormer dbSuppormer;
    RecyclerView recyclerView;
    ArrayList<SuppormerClass> itemArrayList;
    Question_input_adapter adapter;

    ImageView plus;
    ImageView insert;

    Intent intent;
    String categoryname;

    int num = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_input);

        Button arrowbutton = findViewById(R.id.arrowbutton); // 뒤로 가기 버튼
        insert = findViewById(R.id.insert);
        edit_categoryN = findViewById(R.id.edit_categoryN); // 카테고리 edit
        edit_categoryN.setText(edit_categoryN.getText().toString());
        plus = findViewById(R.id.plus); // 추가 버튼

        dbSuppormer = new DBSuppormer(Question_input.this);

        // 뒤로 가기 버튼
        arrowbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), QuestionChange.class);
                startActivity(intent);
            }
        });

        // 문제 저장 후 문제 리스트 화면으로 가는 버튼
        insert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), QuestionChange.class);
                startActivity(intent);
            }
        });
        
        // 문제 만들기 버튼
        plus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                edit_categoryN.setText(edit_categoryN.getText().toString());

                // 다이얼로그 띄우고 문제 양식 입력받음
                AlertDialog.Builder builder = new AlertDialog.Builder(Question_input.this);
                View view2 = LayoutInflater.from(Question_input.this).inflate(R.layout.question_input_dialog, null, false);
                builder.setView(view2);

                edit_answer = view2.findViewById(R.id.edit_answer);
                edit_img = view2.findViewById(R.id.edit_img);
                edit_img.setImageResource(R.drawable.img);
                Button dialogInput = view2.findViewById(R.id.dialogInput);

                final AlertDialog dialog = builder.create();

                // 이미지 업로드
                edit_img.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent, 1);


                    }
                });

                // input 한 내용 저장
                dialogInput.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        long mNow = System.currentTimeMillis();
                        Date mdate = new Date(mNow);
                        String sdate = String.valueOf(mdate);

                        itemArrayList.add(new SuppormerClass(edit_categoryN.getText().toString(), num , imgBit, edit_answer.getText().toString(), sdate));
                        dbSuppormer.Insert(edit_categoryN.getText().toString(), imgBit,  edit_answer.getText().toString(), sdate);

                        adapter.notifyDataSetChanged();

                        dialog.dismiss();
                        edit_categoryN.setText(edit_categoryN.getText().toString());
                    }
                });

                dialog.show();

            }
        });
       
        recyclerList();
    }

    // 리사이클러뷰, 카드뷰 사용
    public void recyclerList(){
        // recycler view
        recyclerView = findViewById(R.id.contain);
        // List item 생성
        itemArrayList = new ArrayList<SuppormerClass>();

        // adapter 추가 및 layout manager 추가
        adapter = new Question_input_adapter(this, itemArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


    // 이미지 갤러리에서 가져오기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    try {
                        imgBit = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    edit_img.setImageBitmap(imgBit);
                }
                break;
        }
    }


}