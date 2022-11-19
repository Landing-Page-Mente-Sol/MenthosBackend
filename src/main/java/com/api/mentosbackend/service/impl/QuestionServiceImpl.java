package com.api.mentosbackend.service.impl;

import com.api.mentosbackend.entities.Course;
import com.api.mentosbackend.entities.Customer;
import com.api.mentosbackend.entities.Question;
import com.api.mentosbackend.repository.IQuestionRepository;
import com.api.mentosbackend.service.IQuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class QuestionServiceImpl extends CrudServiceImpl<Question, Long> implements IQuestionService {

    private final IQuestionRepository questionRepository;

    public QuestionServiceImpl(IQuestionRepository questionRepository) {
        super(questionRepository);
        this.questionRepository = questionRepository;
    }

    @Override
    public List<Question> findQuestionsByCourse(Course course) throws Exception {
        return this.questionRepository.findQuestionsByCourse(course);
    }

    @Override
    public List<Question> findQuestionsByTitleContaining(String title) throws Exception {
        return this.questionRepository.findQuestionsByTitleContaining(title);
    }

    @Override
    public List<Question> findQuestionsByCustomer(Customer customer) throws Exception {
        return this.questionRepository.findQuestionsByCustomer(customer);
    }

    @Override
    public List<Question> findQuestionsByCourseAndTitleContaining(Course course, String title) throws Exception {
        return this.questionRepository.findQuestionsByCourseAndTitleContaining(course, title);
    }
}
