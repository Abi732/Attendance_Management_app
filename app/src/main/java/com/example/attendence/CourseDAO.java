package com.example.attendence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CourseDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Courses course);

    @Query("SELECT * FROM courses")
    LiveData<List<Courses>> getAllCourses();

    @Delete
    void delete(Courses course);
}
