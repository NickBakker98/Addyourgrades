package com.example.nick0.addyourgrades;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class GradeAdapter extends RecyclerView.Adapter<GradeViewHolder> {

    private Context context;
    private List<Grades> listGrades;

    public GradeAdapter(Context context, List<Grades> listGrades) {
        this.context = context;
        this.listGrades = listGrades;
    }

    @Override
    public GradeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_cell, parent, false);
        return new GradeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final GradeViewHolder holder, final int position) {
        // Gets a single item in the list from its position
        final Grades grades = listGrades.get(position);
        // The holder argument is used to reference the views inside the viewHolder
        // Populate the views with the data from the list
        holder.courseName.setText(grades.getCourseName());
        holder.grade.setText(grades.getGrade());
    }

    @Override
    public int getItemCount(){
        return listGrades.size();
    }
}
