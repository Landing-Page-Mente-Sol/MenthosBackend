package com.api.mentosbackend.repository;

import com.api.mentosbackend.entities.Answer;
import com.api.mentosbackend.entities.Question;
import com.api.mentosbackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IAnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findAnswersByUser(User user);
    List<Answer> findAnswersByQuestion(Question question);
}
