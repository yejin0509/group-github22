package com.example.firststep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class QuestionChange extends AppCompatActivity implements QuestionChangeAdapter.OnItemClickListener {

    DBSuppormer dbSuppormer;
    RecyclerView recyclerView;
    ArrayList<ItemCheck> categoryList;
    QuestionChangeAdapter adapter;
    ImageView deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_change);

        dbSuppormer = new DBSuppormer(QuestionChange.this);

        //뒤로가기 버튼
        ImageView backButton = (ImageView) findViewById(R.id.imageView2);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        ImageView imageView3 = findViewById(R.id.imageView3);
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Question_input.class);
                startActivity(intent);
            }
        });


        // db파일 내보내기, 받아오기
        ImageView imageView4 = findViewById(R.id.imageView4);
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 권한ID를 가져옵니다
                int permission = ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);

                int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE);

                // 권한이 열려있는지 확인
                if (permission == PackageManager.PERMISSION_DENIED || permission2 == PackageManager.PERMISSION_DENIED) {
                    // 마쉬멜로우 이상버전부터 권한을 물어본다
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        // 권한 체크(READ_PHONE_STATE의 requestCode를 1000으로 세팅
                        requestPermissions(
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                1000);
                    }
                    return;
                }


                // 다이얼로그 띄우고 문제 양식 입력받음
                AlertDialog.Builder builder = new AlertDialog.Builder(QuestionChange.this);
                View view2 = LayoutInflater.from(QuestionChange.this).inflate(R.layout.question_change_dialog, null, false);
                builder.setView(view2);

                Button importDB = view2.findViewById(R.id.importDB);
                Button exportDB = view2.findViewById(R.id.exportDB);

                final AlertDialog dialog = builder.create();

                importDB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //외부 저장소(External Storage)가 마운트(인식) 되었을 때 동작
                        //getExternalStorageState() 함수를 통해 외부저장장치가 Mount 되어 있는지를 확인
                        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                            File sd = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(), "Suppormer" + ".db");
                            File data = Environment.getDataDirectory();

                            try{
                                File restoreDB = new File(data, "/data/com.example.firststep/databases/Suppormer.db");

                                FileChannel src = new FileInputStream(sd).getChannel();
                                FileChannel dst = new FileOutputStream(restoreDB).getChannel();
                                dst.transferFrom(src, 0, src.size());

                                src.close();
                                dst.close();

                            } catch (IOException e){
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });

                exportDB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                            File sd = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(), "Suppormer" + ".db");
                            File data = Environment.getDataDirectory();

                            try{
                                File restoreDB = new File(data, "/data/com.example.firststep/databases/Suppormer.db");

                                FileChannel src = new FileInputStream(restoreDB).getChannel();
                                FileChannel dst = new FileOutputStream(sd).getChannel();
                                dst.transferFrom(src, 0, src.size());

                                src.close();
                                dst.close();

                            } catch (IOException e){
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        deleteButton = findViewById(R.id.imageView);

        recyclerList();
        adapter.setOnItemClickListener(this);
    }

    // 리사이클러뷰, 카드뷰 사용
    public void recyclerList() {
        // recycler view
        recyclerView = findViewById(R.id.recyclerView);
        // List item 생성
        categoryList = new ArrayList<ItemCheck>();

        List list = dbSuppormer.getResultCategoryN();

        for (int i = 0; i < list.size(); i++) {
            categoryList.add(new ItemCheck(String.valueOf(list.get(i)), false, "View.GONE"));
        }

        // adapter 추가 및 layout manager 추가
        adapter = new QuestionChangeAdapter(this, this, categoryList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


    public void onItemClicked(View view, int position, String category) {
        Intent intent = new Intent(getApplicationContext(), Question_input_2.class);
        intent.putExtra("해당 카테고리 이름", category);
        startActivity(intent);
    }

    // 권한 체크 이후로직
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grandResults) {
        // READ_PHONE_STATE의 권한 체크 결과를 불러온다
        super.onRequestPermissionsResult(requestCode, permissions, grandResults);
        if (requestCode == 1000) {
            boolean check_result = true;

            // 모든 퍼미션을 허용했는지 체크
            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }

            // 권한 체크에 동의를 하지 않으면 안드로이드 종료
            if (check_result == true) {

            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // 권한 체크(READ_PHONE_STATE의 requestCode를 1000으로 세팅
                    requestPermissions(
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                            1000);
                }
                return;
            }
        }
    }
}