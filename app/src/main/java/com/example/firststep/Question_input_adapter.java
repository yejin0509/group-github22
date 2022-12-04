package com.example.firststep;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;

public class Question_input_adapter extends RecyclerView.Adapter<Question_input_adapter.MyViewHolder> { //viewHolder 은 layout 객체에 존재하는 view를 보관하는 holder 객체

    private ArrayList<SuppormerClass> itemArrayList;
    private Context mContext;
    DBSuppormer dbSuppormer;



    String sdate;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        ImageView image;
        TextView answerText;

        public MyViewHolder(View v){
            super(v);

            image = v.findViewById(R.id.view_img);
            answerText = v.findViewById(R.id.view_answer);

            v.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
            MenuItem Edit = menu.add(Menu.NONE, 1001, 1, "편집");
            MenuItem Delete = menu.add(Menu.NONE, 1002, 2, "삭제");
            Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);
        }

        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case 1001:
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        View view = LayoutInflater.from(mContext).inflate(R.layout.question_input_dialog, null, false);
                        builder.setView(view);

                        EditText edit_answer = view.findViewById(R.id.edit_answer);
                        ImageView edit_img = view.findViewById(R.id.edit_img);
                        Button dialogInput = view.findViewById(R.id.dialogInput);

                        dbSuppormer = new DBSuppormer(mContext);


                        dialogInput.setText("등록");

                        String categoryN = itemArrayList.get(getAdapterPosition()).getCategoryN();
                        edit_answer.setText(itemArrayList.get(getAdapterPosition()).getAnswer());
                        edit_img.setImageBitmap(itemArrayList.get(getAdapterPosition()).getImage());


                        int num = (int) itemArrayList.get(getAdapterPosition()).getNum();
                        Bitmap imgbit = (Bitmap) itemArrayList.get(getAdapterPosition()).getImage();


                        final AlertDialog dialog = builder.create();
                        dialogInput.setOnClickListener(new View.OnClickListener(){
                            public void onClick(View v){
                                long mNow = System.currentTimeMillis();
                                Date mdate = new Date(mNow);
                                String ndate = String.valueOf(mdate);
                                sdate = (String) itemArrayList.get(getAdapterPosition()).getWriteDate();

                                itemArrayList.set(getAdapterPosition(),new SuppormerClass(categoryN, num, imgbit, edit_answer.getText().toString(), ndate));
                                dbSuppormer.Update(edit_answer.getText().toString(), ndate, sdate);

                                notifyDataSetChanged();


                                Log.i("현재 ",ndate);
                                Log.i("과거 ", sdate);
                                dialog.dismiss();
                                dbSuppormer.close();
                            }
                        });
                        dialog.show();
                        break;

                    case 1002:
                        String wdate = (String) itemArrayList.get(getAdapterPosition()).getWriteDate();

                        itemArrayList.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(), itemArrayList.size());

                        dbSuppormer.Delete(wdate);
                        break;
                }
                return true;
            }
        };

    }

    public Question_input_adapter(Context context, ArrayList<SuppormerClass> list){
        itemArrayList = list;
        mContext = context;
    }

    public Question_input_adapter(ArrayList<SuppormerClass> itemArrayList){
        this.itemArrayList = itemArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { // viewHolder 생성 시에 호출
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_input2,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) { // 스크롤 등으로 특정 position의 data를 새롭게 표시해야할때마다 호출
        holder.image.setImageBitmap((Bitmap) itemArrayList.get(position).getImage());
        holder.answerText.setText(itemArrayList.get(position).getAnswer());
    }



    @Override
    public int getItemCount() { // 전체 item 개수 반환
        return itemArrayList.size();
    }
}

