package com.api.mentosbackend.service;

import com.api.mentosbackend.entities.Answer;
import com.api.mentosbackend.entities.Customer;
import com.api.mentosbackend.entities.Question;

import java.util.List;

public interface IAnswerService extends CrudService<Answer, Long> {
    List<Answer> findAnswersByCustomer(Customer customer) throws Exception;
    List<Answer> findAnswersByQuestion(Question question) throws Exception;
}
