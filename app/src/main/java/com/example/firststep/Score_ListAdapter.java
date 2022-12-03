package com.example.firststep;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Score_ListAdapter extends RecyclerView.Adapter<Score_ListAdapter.ViewHolder> {
    ArrayList<ScoreResult> scoreL;
    ArrayList<String> countL;
    Context mContext;
    DBUser dbUser;
    String categoryN;
    String resultDate = null;

    interface OnItemClickListener{
        void onItemClick(View view, int position, String category, String resultDate);
    }
    OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public Score_ListAdapter(ArrayList<ScoreResult> scoreL,ArrayList<String> countL,Score score){
        this.scoreL=scoreL;
        this.countL=countL;
        this.mContext=score;
        dbUser=new DBUser(mContext);
    }


    //ViewHolder Class
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title, score, date, count;


        public ViewHolder(@NonNull View v) {
            super(v);

            title=v.findViewById(R.id.score_categoryN);
            score=v.findViewById(R.id.score);
            date=v.findViewById(R.id.score_date);
            count=v.findViewById(R.id.count);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder= LayoutInflater.from(parent.getContext()).inflate(R.layout.score_list,parent, false);
        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull Score_ListAdapter.ViewHolder holder, int position) {
        holder.title.setText(scoreL.get(position).getTitle());
        holder.score.setText(String.valueOf(scoreL. get(position).getCorrect()));
        holder.count.setText(String.valueOf(countL.get(position)));

        categoryN = String.valueOf(scoreL.get(position).getTitle());

        String date1=scoreL.get(position).getWriteDate();
        resultDate= date1.substring(0,9);
        holder.date.setText(resultDate);
        Log.i("카테고리 이름", categoryN);
        Log.i("날짜", resultDate);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(holder.itemView, position, scoreL.get(position).getTitle(),date1);
                Log.i("categoryPosition", String.valueOf(scoreL.get(position).getTitle()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return scoreL.size();
    }

}
