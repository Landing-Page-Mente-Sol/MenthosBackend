package com.api.mentosbackend.service;

import com.api.mentosbackend.entities.Answer;
import com.api.mentosbackend.entities.Question;
import com.api.mentosbackend.entities.User;
import com.api.mentosbackend.repository.IAnswerRepository;
import com.api.mentosbackend.service.impl.AnswerServiceImpl;

import java.util.List;
import java.util.Optional;

import com.api.mentosbackend.util.AnswerGenerator;
import com.api.mentosbackend.util.QuestionGenerator;
import com.api.mentosbackend.util.UserGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AnswerServiceImplTest {
    @Mock
    private IAnswerRepository answerRepository;
    @InjectMocks
    private AnswerServiceImpl answerService;

    @Test
    public void saveTest() throws Exception {
        Answer answer = AnswerGenerator.answer();

        given(answerRepository.save(answer)).willReturn(answer);

        Answer answerSaved = answerService.save(answer);

        assertThat(answerSaved).isNotNull();

        assertEquals(answerSaved, answer);
    }

    @Test
    public void deleteTest() throws Exception {
        Long id = 1L;
        answerService.delete(id);

        verify(answerRepository, times(1)).deleteById(id);
    }

    @Test
    public void getAllTest() throws Exception {
        List<Answer> answers = AnswerGenerator.answers();

        given(answerRepository.findAll()).willReturn(answers);

        List<Answer> answersExpected = answerService.getAll();

        assertEquals(answersExpected, answers);
    }

    @Test
    public void getByIdTest() throws Exception {
        Long id = 1L;
        Answer answer = AnswerGenerator.answer(id);

        given(answerRepository.findById(id)).willReturn(Optional.of(answer));

        Optional<Answer> answerExpected = answerService.getById(id);

        assertThat(answerExpected).isNotNull();

        assertEquals(answerExpected, Optional.of(answer));
    }

    @Test
    public void findAnswersByUserTest() throws Exception {
        User user = UserGenerator.user();
        List<Answer> answers = AnswerGenerator.answers();
        answers.forEach(answer -> answer.setUser(user));

        given(answerRepository.findAnswersByUser(user)).willReturn(answers);

        List<Answer> answersExpected = answerService.findAnswersByUser(user);

        assertEquals(answersExpected, answers);
    }

    @Test
    public void findAnswersByQuestionTest() throws Exception {
        Question question = QuestionGenerator.question();
        List<Answer> answers = AnswerGenerator.answers();
        answers.forEach(answer -> answer.setQuestion(question));

        given(answerRepository.findAnswersByQuestion(question)).willReturn(answers);

        List<Answer> answersExpected = answerService.findAnswersByQuestion(question);

        assertEquals(answersExpected, answers);
    }
}
