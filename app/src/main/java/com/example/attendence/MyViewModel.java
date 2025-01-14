package com.example.attendence;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class MyViewModel extends AndroidViewModel {

    private Repository myRepository;

    private LiveData<List<Courses>> allCourses;
    public MyViewModel(@NonNull Application application) {
        super(application);
        this.myRepository = new Repository(application);
    }

    public LiveData<List<Courses>> getAllCourses() {
        allCourses = myRepository.getAllCourses();
        return allCourses;
    }

    public void addNewCourses(Courses courses){
        myRepository.addCourses(courses);
    }
    public void deleteCourse(Courses courses){
        myRepository.delete(courses);
    }
}
