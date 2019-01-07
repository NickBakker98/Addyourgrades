package com.example.nick0.addyourgrades;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class GradeViewHolder extends RecyclerView.ViewHolder {

    public TextView courseName;
    public TextView grade;
    public View view;

    public GradeViewHolder (View itemView){
        super(itemView);
        courseName = itemView.findViewById(R.id.courseTitle);
        grade = itemView.findViewById(R.id.courseGrade);
        view = itemView;
    }
}
