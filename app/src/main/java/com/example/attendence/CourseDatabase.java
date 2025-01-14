package com.example.attendence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Courses.class},version=1)
public abstract class CourseDatabase extends RoomDatabase {
    public abstract CourseDAO courseDAO();

    //Singelton pattern - to make sure not to make more than one instances of database
    private static CourseDatabase dbInstance;
    public static synchronized CourseDatabase getInstance(Context context){
        if(dbInstance==null){
            dbInstance = Room.databaseBuilder(context.getApplicationContext(), CourseDatabase.class, "course_db")
            .fallbackToDestructiveMigration()
                    .build();

        }
        return dbInstance;
    }


}
