package com.api.mentosbackend.service;

import com.api.mentosbackend.entities.Course;
import com.api.mentosbackend.entities.Question;
import com.api.mentosbackend.entities.User;
import com.api.mentosbackend.repository.IQuestionRepository;
import com.api.mentosbackend.service.impl.QuestionServiceImpl;
import com.api.mentosbackend.util.CourseGenerator;
import com.api.mentosbackend.util.QuestionGenerator;
import com.api.mentosbackend.util.UserGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
public class QuestionServiceImplTest {
    @Mock
    private IQuestionRepository questionRepository;

    @InjectMocks
    private QuestionServiceImpl questionService;

    @Test
    public void saveTest() throws Exception {
        Question question = QuestionGenerator.question();

        given(questionRepository.save(question)).willReturn(question);

        Question questionSaved = questionService.save(question);

        assertThat(questionSaved).isNotNull();
        assertEquals(questionSaved, question);
    }

    @Test
    public void deleteTest() throws Exception {
        Long id = 1L;
        questionService.delete(id);
        verify(questionRepository, times(1)).deleteById(id);
    }

    @Test
    public void getAllTest() throws Exception {
        List<Question> questions = QuestionGenerator.questions();
        given(questionRepository.findAll()).willReturn(questions);

        List<Question> questionsExpected = questionService.getAll();

        assertEquals(questionsExpected, questions);
    }

    @Test
    public void getByIdTest() throws Exception {
        Long id = 1L;
        Question question = QuestionGenerator.question(id);

        given(questionRepository.findById(id)).willReturn(Optional.of(question));

        Optional<Question> questionExpected = questionService.getById(id);

        assertThat(questionExpected).isNotNull();

        assertEquals(questionExpected, Optional.of(question));
    }

    @Test
    public void findQuestionsByCourseTest() throws Exception {
        Course course = CourseGenerator.course();
        List<Question> questions = QuestionGenerator.questions();
        questions.forEach(question -> question.setCourse(course));

        given(questionRepository.findQuestionsByCourse(course)).willReturn(questions);

        List<Question> questionsExpected = questionService.findQuestionsByCourse(course);

        assertEquals(questionsExpected, questions);
    }

    @Test
    public void findQuestionsByTitleContainingTest() throws Exception {
        String keyword = "Question of";
        List<Question> questions = QuestionGenerator.questions();

        questions.forEach(question -> question.setTitle(keyword));

        given(questionRepository.findQuestionsByTitleContaining(keyword)).willReturn(questions);

        List<Question> questionsExpected = questionService.findQuestionsByTitleContaining(keyword);

        assertEquals(questionsExpected, questions);
    }

    @Test
    public void findQuestionsByUserTest() throws Exception {
        User user = UserGenerator.user();
        List<Question> questions = QuestionGenerator.questions();
        questions.forEach(question -> question.setUser(user));

        given(questionRepository.findQuestionsByUser(user)).willReturn(questions);

        List<Question> questionsExpected = questionService.findQuestionsByUser(user);

        assertEquals(questionsExpected, questions);
    }

    @Test
    public void findQuestionsByCourseAndTitleContainingTest() throws Exception {
        String keyword = "Question of";
        Course course = CourseGenerator.course();

        List<Question> questions = QuestionGenerator.questions();
        questions.forEach(question -> {question.setCourse(course); question.setTitle(keyword);});

        given(questionRepository.findQuestionsByCourseAndTitleContaining(course, keyword)).willReturn(questions);

        List<Question> questionsExpected = questionService.findQuestionsByCourseAndTitleContaining(course, keyword);

        assertEquals(questionsExpected, questions);
    }
}
