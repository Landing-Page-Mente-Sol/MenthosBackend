package com.api.mentosbackend.repository;

import com.api.mentosbackend.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
//JpaRepository<entidad a la que pertenece, tipo de dato el ID de la entidad>
public interface ICourseRepository extends JpaRepository<Course, Long> {
}
