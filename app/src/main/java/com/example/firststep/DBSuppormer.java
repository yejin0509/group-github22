package com.example.firststep;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

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

    //insert table 데이터 입력
    public void Insert(String categoryN, Bitmap image, String answer, String writeDate){
        SQLiteDatabase mDB = this.getWritableDatabase();
        Bitmap imageToStoreBitmap=image;

        //이미지를 저장하려면 byte로 변환해야함.
        mByteArrayOutputStream=new ByteArrayOutputStream();
        imageToStoreBitmap.compress(Bitmap.CompressFormat.PNG,100,mByteArrayOutputStream);
        imageInBytes=mByteArrayOutputStream.toByteArray();

        ContentValues cv = new ContentValues();
        cv.put("categoryN", categoryN);
        cv.put("image",imageInBytes);
        cv.put("answer", answer);
        cv.put("writeDate", writeDate);
        long id = mDB.insert("Qtable",null,cv);

        mDB.close();
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

    public List getResult(){
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        List mList = new ArrayList();

        Cursor cursor = db.rawQuery("SELECT * FROM Qtable WHERE id = '1' ", null);
        while(cursor.moveToNext()){

            byte[] image = cursor.getBlob(2);
            Bitmap bm = BitmapFactory.decodeByteArray(image, 0, image.length);

            mList.add(0,cursor.getInt(0));
            mList.add(1,cursor.getString(1));
            mList.add(2,bm);
            mList.add(3,cursor.getString(3));
            mList.add(4,cursor.getString(4));



        }
        return mList;
    }

}
