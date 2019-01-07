package com.example.nick0.addyourgrades;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GradeRepository {
    private AppDatabase mAppDatabase;
    private GradeDAO mGradeDAO;
    private LiveData<List<Grades>> mGrades;
    private Executor mExecutor = Executors.newSingleThreadExecutor();

    public GradeRepository (Context context) {
        mAppDatabase = AppDatabase.getInstance(context);
        mGradeDAO = mAppDatabase.gradeDao();
        mGrades = mGradeDAO.getAllGrades();
    }

    public LiveData<List<Grades>> getAllGrades() {
        return mGrades;
    }

    public void insert(final Grades grade) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mGradeDAO.insertGrades(grade);
            }
        });
    }

    public void update(final Grades grade) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mGradeDAO.updateGrades(grade);
            }
        });
    }

    public void delete(final Grades grade) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mGradeDAO.deleteGrades(grade);
            }
        });
    }
}
