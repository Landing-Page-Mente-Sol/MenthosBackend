package com.api.mentosbackend.service;

import com.api.mentosbackend.entities.Answer;
import com.api.mentosbackend.entities.Question;
import com.api.mentosbackend.entities.User;

import java.util.List;

public interface IAnswerService extends CrudService<Answer, Long> {
    List<Answer> findAnswersByUser(User user) throws Exception;
    List<Answer> findAnswersByQuestion(Question question) throws Exception;
}
