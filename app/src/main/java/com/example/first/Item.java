package com.example.first;

public class Item {
    //int img;
    int num;
    String contents;

    public Item(int num, String contents){
        //this.img = img;
        this.num = num;
        this.contents = contents;
    }

//    public int getImg(){ return img; }
//    public void setImg(int img){
//        this.img = img;
//    }

    public int getNum(){
        return num;
    }
    public void setNum(int num){
        this.num = num;
    }

    public String getContents(){
        return contents;
    }
    public void setContents(String contents){
        this.contents = contents;
    }
}
