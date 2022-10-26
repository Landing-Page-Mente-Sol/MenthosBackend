package com.api.mentosbackend.repository;

import com.api.mentosbackend.entities.Course;
import com.api.mentosbackend.entities.Question;
import com.api.mentosbackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IQuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findQuestionsByCourse(Course course);
    List<Question> findQuestionsByTitleContaining(String title);
    List<Question> findQuestionsByUser(User user);
    List<Question> findQuestionsByCourseAndTitleContaining(Course course, String title);
}
