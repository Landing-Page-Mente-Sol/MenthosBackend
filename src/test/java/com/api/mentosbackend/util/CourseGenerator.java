package com.api.mentosbackend.util;

import com.api.mentosbackend.entities.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseGenerator {
    public static Course course(Long id) {
        return new Course(id, "Course name", "Course image link");
    }

    public static Course course() {
        return course(1L);
    }

    public static List<Course> courses(int size) {
        List<Course> courses = new ArrayList<>();

        for(long i = 0L; i < size; ++i)
            courses.add(course(i));

        return courses;
    }

    public static List<Course> courses(){
        return courses(10);
    }
}
