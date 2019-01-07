package com.example.nick0.addyourgrades;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface GradeDAO {

    @Query("SELECT * FROM grades")
//    List<Grades> getAllGrades();
    LiveData<List<Grades>> getAllGrades();

    @Insert
    void insertGrades(Grades grades);

    @Delete
    void deleteGrades(Grades grades);

    @Update
    void updateGrades(Grades grades);
}
