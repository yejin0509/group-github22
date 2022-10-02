package com.example.first;

public class Item { //데이터가 들어갈 수 있는 클래스 정의
    int num;
    String contents;
    int img;

    public Item(int num, String contents, int img){
        this.num = num;
        this.contents = contents;
        this.img = img;
    }
    public int getNum (){
        return num;
    }
    public void setNum(int num){
        this.num = num;
    }

    public String getContents (){
        return contents;
    }
    public void setContents(String contents){
        this.contents = contents;
    }

    public int getImg (){
        return img;
    }
    public void setImg(int img){
        this.img = img;
    }


}
