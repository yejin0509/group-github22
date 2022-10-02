package com.example.first;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AddAdapter extends RecyclerView.Adapter<AddAdapter.MyViewHolder> {

    ArrayList <Item> itemArrayList;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView numText;
        TextView contentsText;
        ImageView imgView;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            this.numText = itemView.findViewById(R.id.num);
            this.contentsText = itemView.findViewById(R.id.contents);
            imgView = itemView.findViewById(R.id.img);
        }
    }

    public AddAdapter(ArrayList<Item> itemArrayList){
        this.itemArrayList = itemArrayList;
    }
    @NonNull
    @Override
    public AddAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_item,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddAdapter.MyViewHolder holder, int position) {
        holder.numText.setText(String.valueOf(itemArrayList.get(position).getNum()));
        holder.contentsText.setText(itemArrayList.get(position).getContents());
        holder.imgView.setImageResource(itemArrayList.get(position).getImg());
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

}
