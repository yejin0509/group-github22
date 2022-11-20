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

}
