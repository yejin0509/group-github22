package com.example.firststep;

import android.graphics.Bitmap;

public class ItemCheck {
    private String CategoryN;
    private boolean IsCheck;
    private String ViewCheck;

    public ItemCheck (String categoryN, boolean isCheck, String viewCheck) {
        CategoryN = categoryN;
        this.IsCheck = isCheck;
        this.ViewCheck = viewCheck;
    }

    public String getCategoryN() {
        return CategoryN;
    }

    public void setCategoryN(String categoryN) {
        CategoryN = categoryN;
    }

    public boolean getIsCheck(){
        return IsCheck;
    }

    public void setIsCheck(boolean isCheck){
        IsCheck = isCheck;
    }

    public String getViewCheck() {
        return ViewCheck;
    }

    public void setViewCheck(String viewCheck) {
        ViewCheck = viewCheck;
    }
}