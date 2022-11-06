package com.api.mentosbackend.controller;

import com.api.mentosbackend.entities.Answer;
import com.api.mentosbackend.entities.Question;
import com.api.mentosbackend.entities.User;
import com.api.mentosbackend.service.impl.AnswerServiceImpl;
import com.api.mentosbackend.service.impl.QuestionServiceImpl;
import com.api.mentosbackend.service.impl.UserServiceImpl;
import com.api.mentosbackend.util.AnswerGenerator;
import com.api.mentosbackend.util.QuestionGenerator;
import com.api.mentosbackend.util.UserGenerator;
import com.api.mentosbackend.util.UtilFunctions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = AnswerController.class)
@ActiveProfiles("test")
public class AnswerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AnswerServiceImpl answerService;
    @MockBean
    private  UserServiceImpl userService;
    @MockBean
    private  QuestionServiceImpl questionService;

    List<Answer> answers;

    private final String basePath = "/api/v1/answers";

    @BeforeEach
    void setup() { this.answers = AnswerGenerator.answers(); }

    @Test
    void findAllAnswersTest() throws Exception {
        given(answerService.getAll()).willReturn(this.answers);

        mockMvc.perform(get(this.basePath)).andExpect(status().isOk());
    }

    @Test
    void findAnswerByIdTest() throws Exception {
        Long id = 1L;
        Answer answer = AnswerGenerator.answer(id);

        given(this.answerService.getById(id)).willReturn(Optional.of(answer));

        mockMvc.perform(get(this.basePath + "/{id}", id)).andExpect(status().isOk());
    }

    @Test
    void insertAnswerTest() throws Exception {
        Long questionId = 1L;
        Long userId = 1L;
        Long id = 1L;

        Question question = QuestionGenerator.question(questionId);
        User user = UserGenerator.user(userId);
        Answer answer = AnswerGenerator.answer(id);

        answer.setQuestion(question);
        answer.setUser(user);

        given(questionService.getById(questionId)).willReturn(Optional.of(question));
        given(userService.getById(userId)).willReturn(Optional.of(user));
        given(answerService.getById(id)).willReturn(Optional.of(answer));

        mockMvc.perform(post(this.basePath + "/{questionId}/{userId}", questionId, userId)
                        .content(UtilFunctions.objectAsJson(answer))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());

    }

    @Test
    void updateAnswerTest() throws Exception {
        Long id = 1L;
        Answer answer = AnswerGenerator.answer(id);

        given(answerService.getById(id)).willReturn(Optional.of(answer));

        mockMvc.perform(put(this.basePath + "/{id}", id)
                        .content(UtilFunctions.objectAsJson(answer))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void deleteAnswerTest() throws Exception {
        Long id = 1L;

        Answer answer = AnswerGenerator.answer(id);

        given(answerService.getById(id)).willReturn(Optional.of(answer));

        mockMvc.perform(delete(this.basePath + "/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void findAnswersByUserTest() throws Exception {
        Long userId = 1L;
        User user = UserGenerator.user(userId);
        this.answers.forEach(answer -> answer.setUser(user));

        given(userService.getById(userId)).willReturn(Optional.of(user));
        given(answerService.findAnswersByUser(user)).willReturn(answers);

        mockMvc.perform(get(this.basePath + "/search/user/{id}", userId))
                .andExpect(status().isOk());

    }

    @Test
    void findAnswersByQuestionTest() throws Exception {
        Long questionId = 1L;
        Question question = QuestionGenerator.question(questionId);

        this.answers.forEach(answer -> answer.setQuestion(question));

        given(questionService.getById(questionId)).willReturn(Optional.of(question));
        given(answerService.findAnswersByQuestion(question)).willReturn(answers);

        mockMvc.perform(get(this.basePath + "/search/question/{id}", questionId))
                .andExpect(status().isOk());

    }
}
