package com.example.android.coursebookingapp.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "course")
public class Course {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String courseName;

    @ColumnInfo(name = "code")
    public String courseCode;

    /*
    For later deliv

    @ColumnInfo(name = "day")
    public String day;

    @ColumnInfo(name = "hour")
    public String hour;

    @ColumnInfo(name = "capacity")
    public int capacity;*/
}

