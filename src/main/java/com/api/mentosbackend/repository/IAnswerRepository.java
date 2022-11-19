package com.api.mentosbackend.repository;

import com.api.mentosbackend.entities.Answer;
import com.api.mentosbackend.entities.Customer;
import com.api.mentosbackend.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IAnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findAnswersByCustomer(Customer customer);
    List<Answer> findAnswersByQuestion(Question question);
}
