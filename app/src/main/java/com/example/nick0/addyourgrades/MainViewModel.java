package com.example.nick0.addyourgrades;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import java.util.List;

public class MainViewModel extends ViewModel {

    private GradeRepository mRepository;
    private LiveData<List<Grades>> mGrades;

    public MainViewModel(Context context) {
        mRepository = new GradeRepository(context);
        mGrades = mRepository.getAllGrades();
    }

    public LiveData<List<Grades>> getGrades() {
        return mGrades;
    }

    public void insert(Grades grade) {
        mRepository.insert(grade);
    }

    public void update(Grades grade) {
        mRepository.update(grade);
    }

    public void delete(Grades grade) {
        mRepository.delete(grade);
    }
}
