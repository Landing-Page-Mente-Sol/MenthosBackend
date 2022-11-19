package com.api.mentosbackend.service;

import com.api.mentosbackend.entities.Course;
import com.api.mentosbackend.entities.Customer;
import com.api.mentosbackend.entities.Question;

import java.util.List;

public interface IQuestionService extends CrudService<Question, Long> {
    List<Question> findQuestionsByCourse(Course course) throws Exception;
    List<Question> findQuestionsByTitleContaining(String title) throws Exception;
    List<Question> findQuestionsByCustomer(Customer customer) throws Exception;

    List<Question> findQuestionsByCourseAndTitleContaining(Course course, String title) throws Exception;
}
