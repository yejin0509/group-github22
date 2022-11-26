package com.example.firststep;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class QuestionChangeAdapter extends RecyclerView.Adapter<QuestionChangeAdapter.MyViewHolder> {
    private static final int REQ_CODE = 123;
    //viewHolder 은 layout 객체에 존재하는 view를 보관하는 holder 객체

    public interface OnItemClickListener{
        void onItemClicked(View view, int position, String data);
    }

    OnItemClickListener itemClickListener;

    public void setOnItemClickListener (OnItemClickListener listener){
        itemClickListener = listener;
    }

    ArrayList<ItemCheck> categoryList;
    QuestionChange questionChange;

    List<String> checkPosition = new ArrayList();
    List<String> checkCategoryN = new ArrayList();
    ArrayList<SuppormerClass> itemarraylist;
    private Context mContext;
    DBSuppormer dbSuppormer;
    CheckBox checkBox2;
    myDBHelper myDBHelper;

    ImageView deleteButton;
    ImageView uploadButton;
    ImageView checkButton;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView categoryText;
        CheckBox checkBox;

        public MyViewHolder(View v){
            super(v);

            categoryText = v.findViewById(R.id.view_category);
            checkBox = v.findViewById(R.id.checkBox);
        }
    }

    public QuestionChangeAdapter(Context context,QuestionChange questionChange, ArrayList<ItemCheck> categoryList){
        this.categoryList = categoryList;
        this.questionChange = questionChange;
        mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { // viewHolder 생성 시에 호출
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_change2,parent,false);

        checkBox2 = view.findViewById(R.id.checkBox);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) { // 스크롤 등으로 특정 position의 data를 새롭게 표시해야할때마다 호출
        holder.categoryText.setText(String.valueOf(categoryList.get(position).getCategoryN()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClicked(holder.itemView, position, String.valueOf(categoryList.get(position).getCategoryN()));
            }
        });

        // 체크박스
        holder.checkBox.setOnClickListener(v -> {
            if(holder.checkBox.isChecked()){
                categoryList.get(position).setIsCheck(true);
                checkPosition.add(String.valueOf(position));
                checkCategoryN.add(categoryList.get(position).getCategoryN());
            } else {
                categoryList.get(position).setIsCheck(false);
                checkPosition.remove(String.valueOf(position));
                checkCategoryN.remove(categoryList.get(position).getCategoryN());
            }
        });

        if(categoryList.get(position).getIsCheck() == true){
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }

        deleteButton = questionChange.deleteButton;
        uploadButton = questionChange.imageView4;
        checkButton = questionChange.checkButton;

        dbSuppormer = new DBSuppormer(mContext);

        checkBox2.setVisibility(View.GONE);

        if(categoryList.get(0).getShowCheck() == "View.VISIBLE"){
            checkBox2.setVisibility(View.VISIBLE);
        } else {
            checkBox2.setVisibility(View.GONE);
        }

        // db 내보내기, 불러오기 버튼
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 다이얼로그 띄우고 문제 양식 입력받음
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                View view2 = LayoutInflater.from(mContext).inflate(R.layout.question_change_dialog, null, false);
                builder.setView(view2);

                Button importDB = view2.findViewById(R.id.importDB);
                Button exportDB = view2.findViewById(R.id.exportDB);

                final AlertDialog dialog = builder.create();

                importDB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        questionChange.selectDB();

                        dialog.dismiss();

                    }
                });

                exportDB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkButton.setVisibility(View.VISIBLE);
                        deleteButton.setVisibility(View.GONE);
                        uploadButton.setVisibility(View.GONE);
                        for(int i = 0 ; i<categoryList.size(); i++){
                            if(categoryList.get(i).getShowCheck() == "View.GONE"){
                                categoryList.get(i).setShowCheck("View.VISIBLE");
                                notifyItemChanged(i);
                            } else {
                                categoryList.get(i).setShowCheck("View.GONE");
                                notifyItemChanged(i);
                            }
                        }

                        dialog.dismiss();
                    }
                });

                checkButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        exportDB();
                    }
                });


                if(categoryList.size() == 0 ){
                    dialog.show();
                } else {
                    if(holder.checkBox.getVisibility() == View.GONE){
                        dialog.show();
                    }
                }

            }
        });


        // 삭제 버튼 (리스트 삭제, db 삭제)
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(int i = 0 ; i<categoryList.size(); i++){
                    if(categoryList.get(i).getShowCheck() == "View.GONE"){
                        categoryList.get(i).setShowCheck("View.VISIBLE");
                        notifyItemChanged(i);
                    } else {
                        categoryList.get(i).setShowCheck("View.GONE");
                        notifyItemChanged(i);
                    }
                }

                Collections.sort(checkPosition, Collections.reverseOrder());

                for(int n = 0; n<checkPosition.size(); n++ ){
                    dbSuppormer.deleteValue(String.valueOf(categoryList.get(Integer.parseInt(checkPosition.get(n))).getCategoryN()));
                    categoryList.remove(Integer.parseInt(checkPosition.get(n)));
                }


                notifyItemChanged(position);

                checkPosition.clear();
            }
        });


    }
    public void importDB(String fileName){
        //외부 저장소(External Storage)가 마운트(인식) 되었을 때 동작
        //getExternalStorageState() 함수를 통해 외부저장장치가 Mount 되어 있는지를 확인
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File sd = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(), fileName);
            File data = Environment.getDataDirectory();

            try{
                File restoreDB = new File(data, "/data/com.example.firststep/databases/Suppormer2.db");

                FileChannel src = new FileInputStream(sd).getChannel();
                FileChannel dst = new FileOutputStream(restoreDB).getChannel();
                dst.transferFrom(src, 0, src.size());

                src.close();
                dst.close();

                // 가져온 db 파일 데이터를 Qtable에 넣어줌
                myDBHelper = new myDBHelper(mContext);

                itemarraylist = new ArrayList<SuppormerClass>();

                itemarraylist = myDBHelper.getResult();

                for(int i = 0; i<itemarraylist.size();i++){
                    dbSuppormer.Insert(itemarraylist.get(i).getCategoryN(),itemarraylist.get(i).getImage(),itemarraylist.get(i).getAnswer(),itemarraylist.get(i).getWriteDate());

                }

                Intent intent2 = new Intent(mContext, QuestionChange.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(intent2);

                restoreDB.delete();

            } catch (IOException e){
                e.printStackTrace();
                Toast.makeText(mContext,"ERROR",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(mContext,"ERROR",Toast.LENGTH_SHORT).show();
        }
    }

    public void exportDB(){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            String sd = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
            File data = Environment.getDataDirectory();
            File data2 = Environment.getDataDirectory();

            try{
                File restoreDB = new File(data, "/data/com.example.firststep/databases/Suppormer.db");
                File restoreDB2 = new File(data2, "/data/com.example.firststep/databases/Suppormer2.db");

                FileChannel src = new FileInputStream(restoreDB).getChannel();
                FileChannel dst = new FileOutputStream(restoreDB2).getChannel();
                dst.transferFrom(src, 0, src.size());

                src.close();
                dst.close();

                myDBHelper = new myDBHelper(mContext);

                StringBuilder deleteCategory = new StringBuilder();

                if(checkCategoryN.size() == 1){
                    deleteCategory.append(" ' " + checkCategoryN.get(0) + " ' ");
                } else {
                    deleteCategory.append(" '" + checkCategoryN.get(0) + "' ");
                    for(int i = 1; i < checkCategoryN.size(); i++){
                        deleteCategory.append( ", '" +checkCategoryN.get(i) + "' ");
                    }
                }

                myDBHelper.deleteValue2(String.valueOf(deleteCategory));

                List list = myDBHelper.getResultCategoryN2();

                for(int i =0; i<categoryList.size(); i++){
                    categoryList.get(i).setShowCheck("View.GONE");
                    categoryList.get(i).setIsCheck(false);
                    notifyItemChanged(i);
                }

                File sd2 = new File(sd,checkCategoryN.get(0)+".db");

                checkCategoryN.clear();
                deleteCategory.setLength(0);

                checkButton.setVisibility(View.GONE);
                deleteButton.setVisibility(View.VISIBLE);
                uploadButton.setVisibility(View.VISIBLE);

                FileChannel src2 = new FileInputStream(restoreDB2).getChannel();
                FileChannel dst2 = new FileOutputStream(sd2).getChannel();
                dst2.transferFrom(src2, 0, src2.size());

                src2.close();
                dst2.close();

                restoreDB2.delete();

            } catch (IOException e){
                e.printStackTrace();
                Toast.makeText(mContext,"ERROR",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(mContext,"ERROR",Toast.LENGTH_SHORT).show();
        }
    }

    //DB를 생성하고 초기화하는 DB생성자 정의
    public class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context){
            super(context, "Suppormer2.db", null, 1);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS Qtable (id INTEGER, categoryN TEXT NOT NULL," +
                    "image blob NOT NULL, answer TEXT NOT NULL, writeDate TEXT NOT NULL)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS Qtable");
            onCreate(db);
        }

        public void deleteValue2(String categoryN){
            SQLiteDatabase mDB=getWritableDatabase();
            mDB.execSQL("DELETE FROM Qtable WHERE categoryN NOT IN (" +categoryN+ ") ;");

            mDB.close();
        }
        public List getResultCategoryN2(){
            // 읽기가 가능하게 DB 열기
            SQLiteDatabase db = getWritableDatabase();
            List mList = new ArrayList();

            Cursor cursor = db.rawQuery("SELECT DISTINCT categoryN FROM Qtable ", null);
            while(cursor.moveToNext()){

                mList.add(0,cursor.getString(0));

            }
            return mList;
        }
        public ArrayList<SuppormerClass> getResult(){
            // 읽기가 가능하게 DB 열기
            SQLiteDatabase db = getWritableDatabase();
            ArrayList<SuppormerClass> itemArrayList = new ArrayList<SuppormerClass>();

            Cursor cursor = db.rawQuery("SELECT * FROM Qtable", null);
            while(cursor.moveToNext()){

                byte[] image = cursor.getBlob(2);
                Bitmap bm = BitmapFactory.decodeByteArray(image, 0, image.length);

                itemArrayList.add(new SuppormerClass(cursor.getString(1),cursor.getInt(0),bm,cursor.getString(3),cursor.getString(4)));

            }
            return itemArrayList;
        }


    }

    @Override
    public int getItemCount() { // 전체 item 개수 반환
        return categoryList.size();
    }

}