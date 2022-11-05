
package com.example.firststep;

        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import java.util.ArrayList;

public class Class_question_listAdapter extends RecyclerView.Adapter<Class_question_listAdapter.MyViewHolder> { //viewHolder 은 layout 객체에 존재하는 view를 보관하는 holder 객체

    ArrayList<String> categoryList;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView categoryText;

        public MyViewHolder(View v){
            super(v);

            categoryText = v.findViewById(R.id.view_category);
        }
    }

    public Class_question_listAdapter(Class_question_list questionChange, ArrayList<String> categoryList){
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { // viewHolder 생성 시에 호출
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_question_list2,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) { // 스크롤 등으로 특정 position의 data를 새롭게 표시해야할때마다 호출
        holder.categoryText.setText(String.valueOf(categoryList.get(position)));
    }

    @Override
    public int getItemCount() { // 전체 item 개수 반환
        return categoryList.size();
    }
}