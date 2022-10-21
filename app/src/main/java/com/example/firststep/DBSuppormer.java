package com.example.firststep;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;

public class DBSuppormer extends SQLiteOpenHelper {
    Context context;
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "Suppormer.db";

    private ByteArrayOutputStream mByteArrayOutputStream;
    private byte[] imageInBytes;

    public DBSuppormer(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Qtable (id INTEGER PRIMARY KEY AUTOINCREMENT, categoryN TEXT NOT NULL," +
                "image blob NOT NULL, answer TEXT NOT NULL, writeDate TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Qtable");
        onCreate(db);
    }

    //insert table
    public void Insert(SuppormerClass sc){
        try {
            SQLiteDatabase mDB=this.getWritableDatabase();
            Bitmap imageToStoreBitmap=sc.getImage();

            //이미지를 저장하려면 byte로 변환해야함.
            mByteArrayOutputStream=new ByteArrayOutputStream();
            imageToStoreBitmap.compress(Bitmap.CompressFormat.PNG,100,mByteArrayOutputStream);
            imageInBytes=mByteArrayOutputStream.toByteArray();

            //테이블 각 열에 정보 저장하기위해 cv 사용한 것
            ContentValues cv=new ContentValues();
            cv.put("categoryN",sc.getCategoryN());
            cv.put("image",imageInBytes);
            cv.put("answer",sc.getAnswer());
            cv.put("writeDate",sc.getWriteDate());
            //데이터 베이스 Qtable에 cv 넘기기
            long checkIfQueryRuns=mDB.insert("Qtable",null,cv);

            //insert하는 쿼리문이 정상적인지 확인
            if(checkIfQueryRuns!=0){
                Toast.makeText(context,"Data added into Qtable",Toast.LENGTH_SHORT).show();
                mDB.close();
            }
            else{
                Toast.makeText(context,"fails to add Data",Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }


    public void Update(String _categoryN, Bitmap _image, String _answer, String _writeDate,String _beforeDate){
        //beforeDate는 문제 처음 생성시 같이 들어간 날짜고 writeDate는 수정하려는 시기 날짜
        try {
            SQLiteDatabase mDB=this.getWritableDatabase();
            Bitmap imageToStoreBitmap=_image;

            //이미지를 저장하려면 byte로 변환해야함.
            mByteArrayOutputStream=new ByteArrayOutputStream();
            imageToStoreBitmap.compress(Bitmap.CompressFormat.PNG,100,mByteArrayOutputStream);
            imageInBytes=mByteArrayOutputStream.toByteArray();

            //테이블 각 열에 정보 저장하기위해 cv 사용한 것
            ContentValues cv=new ContentValues();
            cv.put("categoryN",_categoryN);
            cv.put("image",imageInBytes);
            cv.put("answer",_answer);
            cv.put("writeDate",_writeDate);

            //데이터 베이스 Qtable에 cv 넘기기
            long checkIfQueryRuns=mDB.update("Qtable",cv, "writeDate=?",new String[]{_beforeDate});

            //update 쿼리문이 정상적인지 확인
            if(checkIfQueryRuns!=0){
                Toast.makeText(context,"Data update success",Toast.LENGTH_SHORT).show();
                mDB.close();
            }
            else{
                Toast.makeText(context,"fails to update",Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }


    public void Delete(String _beforeDate){
        //문제 작성 날짜만 받아서 조건걸어 삭제하기. 이미지 byte 변환없어서 바로 쿼리문 씀.
        SQLiteDatabase mDB=getWritableDatabase();
        mDB.execSQL("DELETE FROM Qtable WHERE writeDate="+_beforeDate+";");
    }

}
