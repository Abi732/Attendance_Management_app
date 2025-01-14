package com.example.attendence;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.databinding.DataBindingUtil;

import com.example.attendence.databinding.CourseSectionAddonBinding;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.CourseviewHolder> {

    private ArrayList<Courses> courses;

    public MyAdapter(ArrayList<Courses> courses) {
        this.courses = courses;
    }

    @NonNull
    @Override
    public CourseviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CourseSectionAddonBinding courseSectionAddonBinding =
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.course_section_addon,
                        parent,
                        false
                );
        return new CourseviewHolder(courseSectionAddonBinding);
    }

    public void setCourses(ArrayList<Courses> courses) {
        this.courses = courses;

        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull CourseviewHolder holder, int position) {

        Courses currentCourses = courses.get(position);

        holder.courseSectionAddonBinding.setCourseSec(currentCourses);
    }

    @Override
    public int getItemCount() {
        return courses != null ? courses.size() : 0;
    }

    private ArrayList<Courses> coursesArrayList;

    class CourseviewHolder extends RecyclerView.ViewHolder{
        private CourseSectionAddonBinding courseSectionAddonBinding;

        public CourseviewHolder(@NonNull  CourseSectionAddonBinding courseSectionBinding) {
            super(courseSectionBinding.getRoot());
            this.courseSectionAddonBinding = courseSectionBinding;
        }
    }

}
