package com.example.attendence;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.attendence.databinding.ActivityMainBinding;
import com.example.attendence.databinding.CourseSectionAddonBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    // Data Binding
    private ActivityMainBinding mainBinding;

    // UI Components
    private LinearLayout courseContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Initialize Data Binding
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        MainActivityClickHandlers handlers = new MainActivityClickHandlers(this);
        mainBinding.setClickHandler(handlers);

        // Initialize UI Components
        courseContainer = findViewById(R.id.linearLayout);
        TextView myTextView = findViewById(R.id.mytextview);
        ImageView myImg = findViewById(R.id.AttendenceImage);

        // Set default text and image
        myTextView.setText("Attendance Manager");
        myImg.setImageResource(R.drawable.attendancelist);

        // Set up ViewModel
        MyViewModel viewModel = new ViewModelProvider(this).get(MyViewModel.class);

        // Observe LiveData to load courses
        viewModel.getAllCourses().observe(this, courses -> {
            if (courses != null && !courses.isEmpty()) {
                populateCourseViews(courses, viewModel);
            } else {
                Log.d(TAG, "No courses found in the database");
            }
        });

        // Adjust layout for window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * Populate the course container with views dynamically.
     *
     * @param courses   List of courses to display.
     * @param viewModel ViewModel for database operations.
     */
    private void populateCourseViews(List<Courses> courses, MyViewModel viewModel) {
        courseContainer.removeAllViews(); // Clear existing views
        LayoutInflater inflater = getLayoutInflater();

        for (Courses course : courses) {
            // Inflate course_section layout
            CourseSectionAddonBinding binding = CourseSectionAddonBinding.inflate(inflater);
            binding.setCourseSec(course); // Bind the course object

            // Set functionality for increment buttons
            binding.incrementButtonAttendence.setOnClickListener(v -> {
                int currentAttendance = course.getAttendance();
                course.setAttendance(currentAttendance + 1);
                binding.setCourseSec(course); // Update UI
                updateCourseInDatabase(course, viewModel); // Save changes to DB
            });

            binding.incrementButtonTNC.setOnClickListener(v -> {
                int currentTNC = course.getTnc();
                course.setTnc(currentTNC + 1);
                binding.setCourseSec(course); // Update UI
                updateCourseInDatabase(course, viewModel); // Save changes to DB
            });

            //set functionality for decrement buttons
            binding.decrementButtonAttendence.setOnClickListener(v -> {
                int currentAttendance = course.getAttendance();
                if (currentAttendance > 0) {
                    course.setAttendance(currentAttendance - 1);
                }
                binding.setCourseSec(course); // Update UI
                updateCourseInDatabase(course, viewModel); // Save changes to DB
            });

            binding.decrementButtonTNC.setOnClickListener(v -> {
                int currentTNC = course.getTnc();
                if (currentTNC > 0) {
                    course.setTnc(currentTNC - 1);
                }
                binding.setCourseSec(course); // Update UI
                updateCourseInDatabase(course, viewModel);// Save changes to DB
            });


            // Set functionality for delete button
            binding.deleteButton.setOnClickListener(v -> {
                courseContainer.removeView(binding.getRoot()); // Remove the view from the container
                viewModel.deleteCourse(course); // Delete the course from the database
            });

            binding.courseName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // No action needed before text is changed
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // Update the course name in real-time as the user types
                    course.setName(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                    // No action needed after text is changed
                }
            });


            // Add the bound view to the container
            courseContainer.addView(binding.getRoot());

        }
    }

    /**
     * Update the course in the database.
     *
     * @param course    The course to update.
     * @param viewModel The ViewModel for database operations.
     */
    private void updateCourseInDatabase(Courses course, MyViewModel viewModel) {
        new Thread(() -> viewModel.addNewCourses(course)).start();
    }
}
