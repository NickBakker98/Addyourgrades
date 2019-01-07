package com.example.nick0.addyourgrades;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcelable;

@Entity(tableName = "grades")
public class Grades {

    public Grades(String courseName, String grade){
        this.courseName = courseName;
        this.grade = grade;
    }

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "courseName")
    private String courseName;

    @ColumnInfo(name = "grade")
    private String grade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }
    public String getGrade(){
        return grade;
    }
    public void setCourseName(String courseName){
        this.courseName = courseName;
    }
    public void setGrade(String grade){
        this.grade = grade;
    }
}
