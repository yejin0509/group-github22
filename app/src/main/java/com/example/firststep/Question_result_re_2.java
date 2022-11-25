
package com.example.firststep;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Question_result_re_2 extends RecyclerView.Adapter<Question_result_re_2.MyViewHolder> { //viewHolder 은 layout 객체에 존재하는 view를 보관하는 holder 객체

    ArrayList<UserClass> questionList = new ArrayList<UserClass>();     //결과 리스트
    ArrayList<SuppormerClass> ImageList = new ArrayList<SuppormerClass>();
    ArrayList<String> numList = new ArrayList<String>();
    int test = 0;
    String answer, user_answer, ch1, ch2, ch3;

    interface OnItemClickListener{
        void onItemClick(View view, int position,String category);
    }

    OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView Number, choice1, choice2, choice3, q1, q2, q3;
        ImageView image, wrong, correct;

        public MyViewHolder(View v){
            super(v);
            Number = v.findViewById(R.id.number);
            choice1 = v.findViewById(R.id.choice1);
            choice2 = v.findViewById(R.id.choice2);
            choice3 = v.findViewById(R.id.choice3);
            image = v.findViewById(R.id.imageView1);
            wrong = v.findViewById(R.id.qResultWrong2);
            correct = v.findViewById(R.id.qResultCorrect2);
            q1 = v.findViewById(R.id.tv_Q1);
            q2 = v.findViewById(R.id.tv_Q2);
            q3 = v.findViewById(R.id.tv_Q3);


            wrong.setVisibility(View.INVISIBLE);
            correct.setVisibility(View.INVISIBLE);


        }
    }

    public Question_result_re_2(Context context, ArrayList<UserClass> questionList, ArrayList<SuppormerClass> ImageList,
                                ArrayList<String> numList){
        this.questionList = questionList;
        this.ImageList = ImageList;
        this.numList = numList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { // viewHolder 생성 시에 호출
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_result_re_2,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) { // 스크롤 등으로 특정 position의 data를 새롭게 표시해야할때마다 호출
        holder.Number.setText(questionList.get(Integer.parseInt(numList.get(test))).getQNumber());
        holder.choice1.setText(questionList.get(Integer.parseInt(numList.get(test))).getChoice1());
        holder.choice2.setText(questionList.get(Integer.parseInt(numList.get(test))).getChoice2());
        holder.choice3.setText(questionList.get(Integer.parseInt(numList.get(test))).getChoice3());

        answer = questionList.get(Integer.parseInt(numList.get(test))).getResultA();
        user_answer = questionList.get(Integer.parseInt(numList.get(test))).getUserA();
        ch1 = questionList.get(Integer.parseInt(numList.get(test))).getChoice1();
        ch2 = questionList.get(Integer.parseInt(numList.get(test))).getChoice1();
        ch3 = questionList.get(Integer.parseInt(numList.get(test))).getChoice1();

        for(int i = 0; i < ImageList.size(); ++i){
            String ImageAnswer = ImageList.get(Integer.parseInt(numList.get(i))).getAnswer();
            if (ImageAnswer.equals(answer)) {
                holder.image.setImageBitmap(ImageList.get(Integer.parseInt(numList.get(i))).getImage());
            }
        }

        if(answer.equals(user_answer)){
            holder.correct.setVisibility(View.VISIBLE);

            //실제 정답이 1번이면
            if(ch1.equals(answer)){
                //초록색
                holder.choice1.setTextColor(Color.parseColor("#568A35"));
                holder.q1.setTextColor(Color.parseColor("#568A35"));
            }

            //실제 정답이 2번이면
            else if(ch2.equals(answer)){
                holder.choice2.setTextColor(Color.parseColor("#568A35"));
                holder.q2.setTextColor(Color.parseColor("#568A35"));
            }

            //실제 정답이 3번이면
            else{
                holder.choice3.setTextColor(Color.parseColor("#568A35"));
                holder.q3.setTextColor(Color.parseColor("#568A35"));
            }
        }

        //유저 != 정답
        else{
            holder.wrong.setVisibility(View.VISIBLE);

            //실제 정답이 1번인데
            if(ch1.equals(answer)){
                //초록색
                holder.choice1.setTextColor(Color.parseColor("#568A35"));
                holder.q1.setTextColor(Color.parseColor("#568A35"));
                //유저 정답이 2번이면
                if(user_answer.equals(ch2)){
                    //붉은색
                    holder.choice2.setTextColor(Color.parseColor("#ff0000"));
                    holder.q2.setTextColor(Color.parseColor("#ff0000"));
                }
                //유저 정답이 3번이면
                else{
                    holder.choice3.setTextColor(Color.parseColor("#ff0000"));
                    holder.q3.setTextColor(Color.parseColor("#ff0000"));
                }
            }
            else if(ch2.equals(answer)){
                holder.choice2.setTextColor(Color.parseColor("#568A35"));
                holder.q2.setTextColor(Color.parseColor("#568A35"));
                if(user_answer.equals(ch1)){
                    holder.choice1.setTextColor(Color.parseColor("#ff0000"));
                    holder.q1.setTextColor(Color.parseColor("#ff0000"));
                }
                else{
                    holder.choice3.setTextColor(Color.parseColor("#ff0000"));
                    holder.q3.setTextColor(Color.parseColor("#ff0000"));
                }
            }
            else{
                holder.choice3.setTextColor(Color.parseColor("#568A35"));
                holder.q3.setTextColor(Color.parseColor("#568A35"));
                if(user_answer.equals(ch2)){
                    holder.choice2.setTextColor(Color.parseColor("#ff0000"));
                    holder.q2.setTextColor(Color.parseColor("#ff0000"));
                }
                else{
                    holder.choice1.setTextColor(Color.parseColor("#ff0000"));
                    holder.q1.setTextColor(Color.parseColor("#ff0000"));
                }
            }
        }

        test += 1;
    }

    @Override
    public int getItemCount() { // 전체 item 개수 반환
        return questionList.size();
    }
}