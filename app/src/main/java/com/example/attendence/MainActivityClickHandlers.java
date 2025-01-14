package com.example.attendence;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import androidx.databinding.DataBindingUtil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.attendence.databinding.CourseSectionAddonBinding;

public class MainActivityClickHandlers {
    private final Context context;

    public MainActivityClickHandlers(Context context) {
        this.context = context;
    }

    public void onFABClicked(View view) {
        // Create a new course with default values
        Courses newCourse = new Courses(0, "New Course", 0, 0);

        // Add the new course to the database
        MyViewModel viewModel = new ViewModelProvider((AppCompatActivity) context).get(MyViewModel.class);
        viewModel.addNewCourses(newCourse);

        // Dynamically add the new course to the LinearLayout
        LinearLayout courseContainer = ((AppCompatActivity) context).findViewById(R.id.linearLayout);
        LayoutInflater inflater = LayoutInflater.from(context);
        CourseSectionAddonBinding binding = CourseSectionAddonBinding.inflate(inflater);

        // Bind the new course to the layout
        binding.setCourseSec(newCourse);

        // Create LayoutParams with 10dp margin on top
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, dpToPx(20), 0, 0); // Apply 10dp margin on top
        binding.getRoot().setLayoutParams(layoutParams);

        // Add the new view to the container
        courseContainer.addView(binding.getRoot());
    }
    private int dpToPx(int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density + 0.5f);
    }


}
