package com.example.wjtest.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.wjtest.Interface.ItemClickListner;
import com.example.wjtest.R;

import javax.annotation.Nullable;

public class RecordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView date, calories, points, activity;
    public ItemClickListner listner;

    public RecordViewHolder(@Nullable View itemView){
        super(itemView);

        date = (TextView) itemView.findViewById(R.id.tv_record_date);
        calories = (TextView) itemView.findViewById(R.id.tv_record_calories);
        points = (TextView) itemView.findViewById(R.id.tv_record_points);
        activity = (TextView) itemView.findViewById(R.id.tv_record_activity);
    }

    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View view) {
        listner.onClick(view, getAdapterPosition(), false);
    }
}
