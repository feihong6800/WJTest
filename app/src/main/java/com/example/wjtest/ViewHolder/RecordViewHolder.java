package com.example.wjtest.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.wjtest.Interface.ItemClickListner;
import com.example.wjtest.Model.RecordInfo;
import com.example.wjtest.R;

import java.util.List;

import javax.annotation.Nullable;

public class RecordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView date, calories, points, activity, minutes;
    public ItemClickListner listner;

    public RecordViewHolder(@Nullable View itemView){
        super(itemView);

        date = (TextView) itemView.findViewById(R.id.tv_record_date);
        calories = (TextView) itemView.findViewById(R.id.tv_record_calories);
        points = (TextView) itemView.findViewById(R.id.tv_record_points);
        activity = (TextView) itemView.findViewById(R.id.tv_record_activity);
        minutes = (TextView) itemView.findViewById(R.id.tv_record_min);

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
