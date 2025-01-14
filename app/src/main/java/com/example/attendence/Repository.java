package com.example.attendence;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {

    private final CourseDAO courseRepo;
    private final ExecutorService executor; // Executor for background operations
    private final Handler handler; // Handler for UI updates

    public Repository(Application application) {
        // Initialize the database and DAO
        CourseDatabase courseDatabase = CourseDatabase.getInstance(application);
        this.courseRepo = courseDatabase.courseDAO();

        // Executor for background database operations
        executor = Executors.newSingleThreadExecutor();

        // Handler for posting updates to the UI thread
        handler = new Handler(Looper.getMainLooper());
    }

    /**
     * Adds a course to the database.
     * This operation is performed on a background thread.
     *
     * @param course The course to be added.
     */
    public void addCourses(Courses course) {
        executor.execute(() -> {
            try {
                courseRepo.insert(course); // Insert the course into the database
            } catch (Exception e) {
                e.printStackTrace(); // Log the error for debugging
            }
        });
    }

    /**
     * Retrieves all courses from the database.
     * This operation is automatically observed using LiveData.
     *
     * @return A LiveData object containing the list of all courses.
     */
    public LiveData<List<Courses>> getAllCourses() {
        return courseRepo.getAllCourses();
    }

    public void delete(Courses course) {
        executor.execute(() -> courseRepo.delete(course));
    }

}
