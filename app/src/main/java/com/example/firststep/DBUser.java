package com.example.firststep;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;

public class DBUser extends SQLiteOpenHelper {
    Context context;
    private static final int DB_VERSION=1;
    private static final String DB_NAME="User.db";

    private ByteArrayOutputStream mByteArrayOutputStream;
    private byte[] imageInBytes;

    public DBUser(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS UserTable (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "QNumber INTEGER NOT NULL, categoryN TEXT NOT NULL, image blob NOT NULL, choice1 TEXT NOT NULL," +
                "choice2 TEXT NOT NULL, choice3 TEXT NOT NULL, userA TEXT NOT NULL," +
                "ResultA TEXT NOT NULL, writeDate TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS UserTable");
        onCreate(db);
    }

    //insert table 데이터 입력
    public void Insert(int QNumber, String categoryN, Bitmap image, String choice1, String choice2, String choice3, String userA, String resultA, String writeDate){
        SQLiteDatabase mDB = this.getWritableDatabase();
        Bitmap imageToStoreBitmap=image;

        //이미지를 저장하려면 byte로 변환해야함.
        mByteArrayOutputStream=new ByteArrayOutputStream();
        imageToStoreBitmap.compress(Bitmap.CompressFormat.PNG,100,mByteArrayOutputStream);
        imageInBytes=mByteArrayOutputStream.toByteArray();

        ContentValues cv = new ContentValues();
        cv.put("QNumber",QNumber);
        cv.put("categoryN", categoryN);
        cv.put("image",imageInBytes);
        cv.put("choice1", choice1);
        cv.put("choice2", choice2);
        cv.put("choice3", choice3);
        cv.put("userA", userA);
        cv.put("resultA", resultA);
        cv.put("writeDate", writeDate);
        long id = mDB.insert("UserTable",null,cv);

        mDB.close();
    }


    public void Update(int _QNumber, String _categoryN, Bitmap _image, String _choice1, String _choice2, String _choice3, String _userA, String _resultA, String _writeDate, String _beforeDate){
        //beforeDate는 문제 풀이시 같이 들어간 날짜고 writeDate는 수정하려는 시기 날짜
        try {
            SQLiteDatabase mDB=this.getWritableDatabase();
            Bitmap imageToStoreBitmap=_image;

            //이미지를 저장하려면 byte로 변환해야함.
            mByteArrayOutputStream=new ByteArrayOutputStream();
            imageToStoreBitmap.compress(Bitmap.CompressFormat.PNG,100,mByteArrayOutputStream);
            imageInBytes=mByteArrayOutputStream.toByteArray();

            //테이블 각 열에 정보 저장하기위해 cv 사용한 것
            ContentValues cv=new ContentValues();
            cv.put("QNumber",_QNumber);
            cv.put("categoryN", _categoryN);
            cv.put("image",imageInBytes);
            cv.put("choice1", _choice1);
            cv.put("choice2", _choice2);
            cv.put("choice3", _choice3);
            cv.put("userA", _userA);
            cv.put("resultA", _resultA);
            cv.put("writeDate", _writeDate);

            //데이터 베이스 usertable에 cv 넘기기
            long checkIfQueryRuns=mDB.update("UserTable",cv, "writeDate=?",new String[]{_beforeDate});

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
        //문제 풀이 날짜만 받아서 조건걸어 삭제하기. 이미지 byte 변환없어서 바로 쿼리문 씀.
        SQLiteDatabase mDB=getWritableDatabase();
        mDB.execSQL("DELETE FROM UserTable WHERE writeDate="+_beforeDate+";");
    }
}
