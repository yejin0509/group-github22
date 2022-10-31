package com.example.firststep;

import android.graphics.Bitmap;

public class UserClass {
    private int id;
    private int QNumber;
    private String categoryN;
    private Bitmap image;
    private String choice1;
    private String choice2;
    private String choice3;
    private String UserA;
    private String ResultA;
    private String writeDate;

    public UserClass(int QNumber, String categoryN, Bitmap image, String choice1, String choice2, String choice3, String userA, String resultA, String writeDate) {
        this.QNumber = QNumber;
        this.categoryN = categoryN;
        this.image = image;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        UserA = userA;
        ResultA = resultA;
        this.writeDate = writeDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQNumber() {
        return QNumber;
    }

    public void setQNumber(int QNumber) {
        this.QNumber = QNumber;
    }

    public String getCategoryN() {
        return categoryN;
    }

    public void setCategoryN(String categoryN) {
        this.categoryN = categoryN;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getChoice1() {
        return choice1;
    }

    public void setChoice1(String choice1) {
        this.choice1 = choice1;
    }

    public String getChoice2() {
        return choice2;
    }

    public void setChoice2(String choice2) {
        this.choice2 = choice2;
    }

    public String getChoice3() {
        return choice3;
    }

    public void setChoice3(String choice3) {
        this.choice3 = choice3;
    }


    public String getUserA() {
        return UserA;
    }

    public void setUserA(String userA) {
        UserA = userA;
    }

    public String getResultA() {
        return ResultA;
    }

    public void setResultA(String resultA) {
        ResultA = resultA;
    }

    public String getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(String writeDate) {
        this.writeDate = writeDate;
    }
}
