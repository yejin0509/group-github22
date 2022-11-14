package com.example.firststep;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class QuestionChangeAdapter extends RecyclerView.Adapter<QuestionChangeAdapter.MyViewHolder> { //viewHolder 은 layout 객체에 존재하는 view를 보관하는 holder 객체

    public interface OnItemClickListener{
        void onItemClicked(View view, int position, String data);
    }

    OnItemClickListener itemClickListener;

    public void setOnItemClickListener (OnItemClickListener listener){
        itemClickListener = listener;
    }

    ArrayList<ItemCheck> categoryList;
    QuestionChange questionChange;

    List<String> checkPosition = new ArrayList();
    private Context mContext;
    DBSuppormer dbSuppormer;
    CheckBox checkBox2;


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView categoryText;
        CheckBox checkBox;

        public MyViewHolder(View v){
            super(v);

            categoryText = v.findViewById(R.id.view_category);
            checkBox = v.findViewById(R.id.checkBox);
        }
    }

    public QuestionChangeAdapter(Context context,QuestionChange questionChange, ArrayList<ItemCheck> categoryList){
        this.categoryList = categoryList;
        this.questionChange = questionChange;
        mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { // viewHolder 생성 시에 호출
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_change2,parent,false);

        checkBox2 = view.findViewById(R.id.checkBox);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) { // 스크롤 등으로 특정 position의 data를 새롭게 표시해야할때마다 호출
        holder.categoryText.setText(String.valueOf(categoryList.get(position).getCategoryN()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClicked(holder.itemView, position, String.valueOf(categoryList.get(position).getCategoryN()));
            }
        });

        // 체크박스
        holder.checkBox.setOnClickListener(v -> {
            if(holder.checkBox.isChecked()){
                categoryList.get(position).setIsCheck(true);
                checkPosition.add(String.valueOf(position));
                Log.i("리스트 추가", String.valueOf(checkPosition));
            } else {
                categoryList.get(position).setIsCheck(false);
                checkPosition.remove(String.valueOf(position));
                Log.i("리스트 감소", String.valueOf(checkPosition));
            }
        });

        if(categoryList.get(position).getIsCheck() == true){
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }

        ImageView deleteButton = questionChange.deleteButton;

        dbSuppormer = new DBSuppormer(mContext);

        checkBox2.setVisibility(View.GONE);

        if(categoryList.get(0).getShowCheck() == "View.VISIBLE"){
            checkBox2.setVisibility(View.VISIBLE);
        } else {
            checkBox2.setVisibility(View.GONE);
        }

        // 삭제 버튼 (리스트 삭제, db 삭제)
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            for(int i = 0 ; i<categoryList.size(); i++){
                if(categoryList.get(i).getShowCheck() == "View.GONE"){
                    categoryList.get(i).setShowCheck("View.VISIBLE");
                    notifyItemChanged(i);
                } else {
                    categoryList.get(i).setShowCheck("View.GONE");
                    notifyItemChanged(i);
                }
            }

            Collections.sort(checkPosition, Collections.reverseOrder());

            for(int n = 0; n<checkPosition.size(); n++ ){
                dbSuppormer.deleteValue(String.valueOf(categoryList.get(Integer.parseInt(checkPosition.get(n))).getCategoryN()));
                categoryList.remove(Integer.parseInt(checkPosition.get(n)));
            }


            notifyItemChanged(position);

            checkPosition.clear();
            }
        });


    }


    @Override
    public int getItemCount() { // 전체 item 개수 반환
        return categoryList.size();
    }

}