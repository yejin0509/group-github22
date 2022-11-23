package com.example.firststep;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.StreamCorruptedException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Score_ListAdapter extends RecyclerView.Adapter<Score_ListAdapter.ViewHolder> {
    private ArrayList<ScoreResult> scoreL;
    private ArrayList<String> categoryL;
    private Context mContext;
    private DBUser dbUser;

    public Score_ListAdapter(ArrayList<ScoreResult> scoreL, Context context){
        this.scoreL=scoreL;
        this.mContext=context;
        dbUser=new DBUser(context);
    }
//    public Score_ListAdapter(Score q, ArrayList<String> scoreL){
//        this.categoryL=scoreL;
//    }

    @NonNull
    @Override
    public Score_ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder= LayoutInflater.from(parent.getContext()).inflate(R.layout.score_list,parent, false);
        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull Score_ListAdapter.ViewHolder holder, int position) {
//        holder.title.setText(String.valueOf(categoryL.get(position)));

        holder.title.setText(scoreL.get(position).getTitle());
        holder.score.setText(String.valueOf(scoreL.get(position).getCount()));

        String date=scoreL.get(position).getWriteDate();
        String resultDate= date.substring(0,9);
        holder.date.setText(resultDate);

    }

    @Override
    public int getItemCount() {
        return scoreL.size();
    }

    //ViewHolder Class
    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private TextView score;
        private TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.score_categoryN);
            score=itemView.findViewById(R.id.score);
            date=itemView.findViewById(R.id.score_date);
        }
    }

    //새로 추가된 정보 db에서 부터 받아 메인에 전달
//    public void addItem(ScoreResult _user){
//        scoreL.add(0,_user);
//        //새로고침
//        notifyItemInserted(0);
//    }
}
