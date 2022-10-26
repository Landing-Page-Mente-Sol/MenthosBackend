package com.api.mentosbackend.repository;

import com.api.mentosbackend.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICourseRepository extends JpaRepository<Course, Long> {
}
