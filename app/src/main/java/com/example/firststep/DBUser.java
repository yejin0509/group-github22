package com.example.firststep;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DBUser extends SQLiteOpenHelper {
    Context context;
    private static final int DB_VERSION=1;
    private static final String DB_NAME="User.db";

    public DBUser(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS UserTable (QNumber TEXT NOT NULL," +
                "categoryN TEXT NOT NULL, choice1 TEXT NOT NULL," +
                "choice2 TEXT NOT NULL, choice3 TEXT NOT NULL, userA TEXT NOT NULL," +
                "ResultA TEXT NOT NULL, writeDate TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS UserTable");
        onCreate(db);
    }

    //insert table 데이터 입력
    public void Insert(String _QNumber, String _categoryN,String _choice1, String _choice2, String _choice3, String _userA, String _resultA, String _writeDate){
        SQLiteDatabase mDB = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("QNumber",_QNumber);
        cv.put("categoryN", _categoryN);
        cv.put("choice1", _choice1);
        cv.put("choice2", _choice2);
        cv.put("choice3", _choice3);
        cv.put("userA", _userA);
        cv.put("ResultA", _resultA);
        cv.put("writeDate", _writeDate);
        long id = mDB.insert("UserTable",null,cv);

        mDB.close();
    }


    public List getCount(){
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        List mList = new ArrayList();

        Cursor cursor = db.rawQuery("SELECT count(*) as count from UserTable GROUP by writeDate;", null);
        while(cursor.moveToNext()){

            mList.add(0,cursor.getInt(0));

        }
        return mList;
    }

    public ArrayList<ScoreResult> getResult(){
        ArrayList<ScoreResult> scoreL=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT categoryN,count(CASE WHEN userA=ResultA then 1 END) as correct, writeDate FROM UserTable GROUP by writeDate;",null);
        if(cursor.getCount()!=0){
            while(cursor.moveToNext()){
                String title=cursor.getString(cursor.getColumnIndex("categoryN"));
                int correct=cursor.getInt(cursor.getColumnIndex("correct"));
                String writeDate=cursor.getString(cursor.getColumnIndex("writeDate"));

                ScoreResult result=new ScoreResult();
                result.setTitle(title);
                result.setCorrect(correct);
                result.setWriteDate(writeDate);
                scoreL.add(result);
            }
        }
        cursor.close();
        return scoreL;
    }

    //Question_result 결과를 위함.
    public ArrayList<UserClass> getResultList(String categoryN, String dateN){
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<UserClass> itemArrayList = new ArrayList<UserClass>();
        UserClass userClass = new UserClass();
        Cursor cursor = db.rawQuery("SELECT QNumber, categoryN, choice1,choice2,choice3,userA, ResultA,writeDate FROM Usertable WHERE categoryN = '"+ categoryN +"' AND writeDate= '"+ dateN +"' ", null);
        while(cursor.moveToNext()){

            itemArrayList.add(new UserClass(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4)
                    ,cursor.getString(5),cursor.getString(6),cursor.getString(7)));
        }
        return itemArrayList;

    }

//    public ArrayList<String> getCategory(){
//        ArrayList<String> categoryL=new ArrayList<>();
//        // 읽기가 가능하게 DB 열기
//        SQLiteDatabase db = getWritableDatabase();
////        List mList = new ArrayList();
//
//        Cursor cursor = db.rawQuery("SELECT categoryN FROM UserTable group by writeDate;", null);
//        if(cursor.getCount() != 0){
//            //무조건 조회 데이터 있을 때 내부 수행
//            while(cursor.moveToNext()){
//                categoryL.add(0,cursor.getString(0));
////                String title=cursor.getString(cursor.getColumnIndex("categoryN"));
////                String score=cursor.getString(cursor.getColumnIndex("correct"));
////                String writeDate=cursor.getString(cursor.getColumnIndex("writeDate"));
////
////                UserClass history=new UserClass();
////                history.setCategoryN(title);
////                history.setScore(score);
////                history.setWriteDate(writeDate);
////                hList.add(history);
//            }
//        }
//        cursor.close();
//
//        return categoryL;
////        while(cursor.moveToNext()){
////
////            Log.i("카테고리 ", cursor.getString(0));
////            mList.add(0,cursor.getString(0));
////
////        }
////        return mList;
//    }

}
