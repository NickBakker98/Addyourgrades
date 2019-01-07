package com.example.nick0.addyourgrades;

public class Grades {

    private String courseName;
    private String grade;

    public Grades(String courseName, String grade){
        this.courseName = courseName;
        this.grade = grade;
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
