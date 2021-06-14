package com.example.android.coursebookingapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface CourseDAO {
    @Query("SELECT * FROM course")
    List<Course> getAll();
    /*
    @Query("SELECT * FROM course WHERE id IN (:courseIds)")
    List<Course> loadAllByIds(int[] courseIds);*/

    @Query("SELECT * FROM course WHERE code LIKE :courseCode LIMIT 1")
    Course findByCode(String courseCode);

    @Query("SELECT * FROM course WHERE name LIKE :courseName LIMIT 1")
    Course findByName(String courseName);

    // We need to insert one course
    //

    @Insert
    void insertAll(Course... courses);

    @Insert(entity = Course.class)
    void insertOneStudent(Course course);

    @Delete
    void delete(Course course);
}
