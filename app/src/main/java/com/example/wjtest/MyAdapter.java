package com.example.wjtest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wjtest.Model.RecordList;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private List<RecordList> listData;

    public MyAdapter(List<RecordList> listData) {
        this.listData = listData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecordList ld=listData.get(position);
        holder.date.setText(ld.getDate());
        holder.activity.setText(ld.getActivity());
        holder.calories.setText(ld.getCalories() + " Calories");
        holder.points.setText(ld.getPoints());
        holder.minutes.setText(ld.getMinutes() + " Minutes");
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView date, calories, points, activity, minutes;

        public ViewHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.tv_record_date);
            calories = (TextView) itemView.findViewById(R.id.tv_record_calories);
            points = (TextView) itemView.findViewById(R.id.tv_record_points);
            activity = (TextView) itemView.findViewById(R.id.tv_record_activity);
            minutes = (TextView) itemView.findViewById(R.id.tv_record_min);
        }
    }
}
