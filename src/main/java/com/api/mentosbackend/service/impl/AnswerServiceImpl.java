package com.api.mentosbackend.service.impl;

import com.api.mentosbackend.entities.Answer;
import com.api.mentosbackend.entities.Question;
import com.api.mentosbackend.entities.User;
import com.api.mentosbackend.repository.IAnswerRepository;
import com.api.mentosbackend.service.IAnswerService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AnswerServiceImpl extends CrudServiceImpl<Answer, Long> implements IAnswerService {

    private final IAnswerRepository answerRepository;
    public AnswerServiceImpl(IAnswerRepository answerRepository) {
        super(answerRepository);
        this.answerRepository = answerRepository;
    }

    @Override
    public List<Answer> findAnswersByUser(User user) throws Exception {
        return this.answerRepository.findAnswersByUser(user);
    }

    @Override
    public List<Answer> findAnswersByQuestion(Question question) throws Exception {
        return this.answerRepository.findAnswersByQuestion(question);
    }
}
