package com.example.firststep;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Question_input extends AppCompatActivity {

    Uri uri;
    Bitmap imgBit;
    BitmapDrawable bitmapDrawable;

    long mNow = System.currentTimeMillis();
    Date mdate = new Date(mNow);
    String sdate = String.valueOf(mdate);

    DBSuppormer dbSuppormer;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_input);

        Button arrowbutton = findViewById(R.id.arrowbutton); // 뒤로가기 버튼
        EditText edit_categoryN = findViewById(R.id.edit_categoryN); // 카테고리 edit
        EditText answer = findViewById(R.id.edit_answer); // 카테고리 edit
        img = findViewById(R.id.img); // 이미지 버튼
        img.setImageResource(R.drawable.img);
        TextView num = findViewById(R.id.num); // 숫자
        ImageView plus = findViewById(R.id.plus); // 추가 버튼
        LinearLayout edit_box = findViewById(R.id.editBox); // edit box

        dbSuppormer = new DBSuppormer(Question_input.this);
        
//        // 레이아웃 추가 버튼 만들기
//        plus.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//
//            }
//        });

        // 이미지 업로드
        img.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityResult.launch(intent);

                img.setImageResource(0);
            }
        });
        
        // 데이터 베이스 저장
        plus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                dbSuppormer.Insert(edit_categoryN.getText().toString(), imgBit,  answer.getText().toString(), sdate);
            }
        });

        // 임시로 데이터 베이스 보여줌
        arrowbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                TextView edit_categoryN2 = findViewById(R.id.edit_categoryN2); // 카테고리 edit
                TextView answer2 = findViewById(R.id.edit_answer2); // 카테고리 edit
                ImageView img2 = findViewById(R.id.img2); // 이미지 버튼
                TextView num2 = findViewById(R.id.num2); // 숫자

                List list = dbSuppormer.getResult();

                edit_categoryN2.setText(String.valueOf(list.get(1)));
                answer2.setText(String.valueOf(list.get(3)));
                num2.setText(String.valueOf(list.get(0)));
                img2.setImageBitmap((Bitmap) list.get(2));

            }
        });

    }

    // 이미지 갤러리에서 가져오기
    ActivityResultLauncher<Intent> startActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        uri = result.getData().getData();

                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            img.setImageBitmap(bitmap);
                            bitmapDrawable = (BitmapDrawable) img.getDrawable();
                            imgBit = bitmapDrawable.getBitmap();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });


}