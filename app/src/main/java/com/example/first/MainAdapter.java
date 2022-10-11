package com.example.first;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> { //viewHolder 은 layout 객체에 존재하는 view를 보관하는 holder 객체

    ArrayList <Item> itemArrayList;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView numText;
        TextView contentsText;

        public MyViewHolder(View v){
            super(v);

            numText = v.findViewById(R.id.editTextNum);
            contentsText = v.findViewById(R.id.editText);
        }
    }

    public MainAdapter(ArrayList<Item> itemArrayList){
        this.itemArrayList = itemArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { // viewHolder 생성 시에 호출
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_sub,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) { // 스크롤 등으로 특정 position의 data를 새롭게 표시해야할때마다 호출
        holder.numText.setText(String.valueOf(itemArrayList.get(position).getNum()));
        holder.contentsText.setText(itemArrayList.get(position).getContents());
    }

    @Override
    public int getItemCount() { // 전체 item 개수 반환
        return itemArrayList.size();
    }
}
