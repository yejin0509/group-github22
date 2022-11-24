package com.example.firststep;

public class UserClass {
    private String QNumber;
    private String categoryN;
    private String choice1;
    private String choice2;
    private String choice3;
    private String UserA;
    private String ResultA;
    private String writeDate;
    public UserClass(){

    }
    public UserClass(String QNumber, String categoryN, String choice1, String choice2, String choice3, String userA, String resultA, String writeDate) {

        this.QNumber = QNumber;
        this.categoryN = categoryN;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        UserA = userA;
        ResultA = resultA;
        this.writeDate = writeDate;
    }

    public String getQNumber() {
        return QNumber;
    }

    public void setQNumber(String QNumber) {
        this.QNumber = QNumber;
    }

    public String getCategoryN() {
        return categoryN;
    }

    public void setCategoryN(String categoryN) {
        this.categoryN = categoryN;
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
