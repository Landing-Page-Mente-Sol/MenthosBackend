package com.api.mentosbackend.service.impl;

import com.api.mentosbackend.entities.Course;
import com.api.mentosbackend.repository.ICourseRepository;
import com.api.mentosbackend.service.ICourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CourseServiceImpl extends CrudServiceImpl<Course, Long> implements ICourseService {

    private final ICourseRepository courseRepository;

    public CourseServiceImpl(ICourseRepository courseRepository) {
        super(courseRepository);
        this.courseRepository = courseRepository;
    }
}
