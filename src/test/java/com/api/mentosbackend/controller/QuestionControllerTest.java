package com.api.mentosbackend.controller;
import com.api.mentosbackend.entities.Course;
import com.api.mentosbackend.entities.Question;
import com.api.mentosbackend.entities.User;
import com.api.mentosbackend.service.impl.CourseServiceImpl;
import com.api.mentosbackend.service.impl.QuestionServiceImpl;
import com.api.mentosbackend.service.impl.UserServiceImpl;
import com.api.mentosbackend.util.CourseGenerator;
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


@WebMvcTest(controllers = QuestionController.class)
@ActiveProfiles("test")
public class QuestionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private QuestionServiceImpl questionService;
    @MockBean
    private CourseServiceImpl courseService;
    @MockBean
    private UserServiceImpl userService;

    private List<Question> questions;
    private final String basePath = "/api/v1/questions";

    @BeforeEach
    void setup() { this.questions = QuestionGenerator.questions(); }

    @Test
    void findAllQuestionsTest() throws Exception {
        given(questionService.getAll()).willReturn(questions);

        mockMvc.perform(get(this.basePath)).andExpect(status().isOk());
    }

    @Test
    void findQuestionByIdTest() throws Exception {
        Long id = 1L;
        Question question = QuestionGenerator.question(id);

        given(questionService.getById(id)).willReturn(Optional.of(question));

        mockMvc.perform(get(this.basePath + "/{id}", id)).andExpect(status().isOk());
    }

    @Test
    void insertQuestionTest() throws Exception {
        Long courseId = 1L;
        Long userId = 1L;
        Long id = 1L;

        Question question = QuestionGenerator.question(id);
        Course course = CourseGenerator.course(courseId);
        User user = UserGenerator.user(userId);

        question.setUser(user);
        question.setCourse(course);

        given(courseService.getById(courseId)).willReturn(Optional.of(course));
        given(userService.getById(userId)).willReturn(Optional.of(user));

        mockMvc.perform(post(this.basePath + "/{courseId}/{userId}", courseId, userId)
                        .content(UtilFunctions.objectAsJson(question))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());

    }

    @Test
    void updateQuestionTest() throws Exception {
        Long id = 1L;

        Question question = QuestionGenerator.question(id);

        given(questionService.getById(id)).willReturn(Optional.of(question));

        mockMvc.perform(put(this.basePath + "/{id}", id)
                        .content(UtilFunctions.objectAsJson(question))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void deleteQuestionTest() throws Exception {
        Long id = 1L;

        Question question = QuestionGenerator.question(id);

        given(questionService.getById(id)).willReturn(Optional.of(question));

        mockMvc.perform(delete(this.basePath + "/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void findQuestionsByCourseTest() throws Exception {
        Long courseId = 1L;
        Long id = 1L;

        Course course = CourseGenerator.course(courseId);

        questions.forEach(question -> question.setCourse(course));

        given(courseService.getById(courseId)).willReturn(Optional.of(course));
        given(questionService.findQuestionsByCourse(course)).willReturn(questions);

        mockMvc.perform(get(this.basePath + "/search/course/{id}", courseId))
                .andExpect(status().isOk());

    }

    @Test
    void findQuestionsByTitleContainingTest() throws Exception {
        String keyword = "Title keyword";
        questions.forEach(question -> question.setTitle(keyword));

        given(questionService.findQuestionsByTitleContaining(keyword)).willReturn(questions);

        mockMvc.perform(get(this.basePath + "/search/title/contains/{keyword}", keyword))
                .andExpect(status().isOk());
    }

    @Test
    void findQuestionsByUserTest() throws Exception {
        Long userId = 1L;
        User user = UserGenerator.user(userId);

        questions.forEach(question -> question.setUser(user));

        given(userService.getById(userId)).willReturn(Optional.of(user));
        given(questionService.findQuestionsByUser(user)).willReturn(questions);

        mockMvc.perform(get(this.basePath + "/search/user/{userId}", userId))
                .andExpect(status().isOk());
    }

    @Test
    void findQuestionsByCourseAndTitleContainingTest() throws Exception {
        Long courseId = 1L;
        String keyword = "Title keyword";

        Course course = CourseGenerator.course(courseId);

        questions.forEach(question -> {question.setCourse(course); question.setTitle(keyword);});

        given(courseService.getById(courseId)).willReturn(Optional.of(course));
        given(questionService.findQuestionsByCourseAndTitleContaining(course, keyword)).willReturn(questions);

        mockMvc.perform(get(this.basePath + "/search/course/{courseId}/title/contains/{keyword}", courseId, keyword))
                .andExpect(status().isOk());
    }


}
