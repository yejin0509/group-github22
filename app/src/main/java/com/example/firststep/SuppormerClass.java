package com.example.firststep;

import android.graphics.Bitmap;

import org.w3c.dom.Text;

//서포머 테이블 정보들 setter getter 함수 클래스
public class SuppormerClass {
    private String CategoryN;
    private int num;
    private Bitmap image;
    private String answer;
    private String writeDate;

    public SuppormerClass(String categoryN, int num, Bitmap image, String answer, String writeDate) {
        CategoryN = categoryN;
        this.num = num;
        this.image = image;
        this.answer = answer;
        this.writeDate = writeDate;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getCategoryN() {
        return CategoryN;
    }

    public void setCategoryN(String categoryN) {
        CategoryN = categoryN;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(String writeDate) {
        this.writeDate = writeDate;
    }
}
