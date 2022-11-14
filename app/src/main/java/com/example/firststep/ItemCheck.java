package com.example.firststep;

import android.graphics.Bitmap;
import android.widget.CheckBox;

public class ItemCheck {
    private String CategoryN;
    private boolean IsCheck;
    private String ShowCheck;

    public ItemCheck (String categoryN, boolean isCheck, String showCheck) {
        CategoryN = categoryN;
        this.IsCheck = isCheck;
        this.ShowCheck = showCheck;
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

    public String getShowCheck(){
        return ShowCheck;
    }

    public void setShowCheck(String showCheck){
        ShowCheck = showCheck;
    }
}