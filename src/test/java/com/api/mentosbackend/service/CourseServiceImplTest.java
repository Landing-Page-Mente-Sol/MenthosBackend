package com.api.mentosbackend.service;

import com.api.mentosbackend.entities.Course;
import com.api.mentosbackend.repository.ICourseRepository;
import com.api.mentosbackend.service.impl.CourseServiceImpl;
import com.api.mentosbackend.util.CourseGenerator;
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
public class CourseServiceImplTest {

    @Mock
    private ICourseRepository courseRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    @Test
    public void saveTest() throws Exception {
        Course course = CourseGenerator.course();

        given(courseRepository.save(course)).willReturn(course);

        Course courseSaved = courseService.save(course);

        assertThat(courseSaved).isNotNull();
        assertEquals(courseSaved, course);
    }

    @Test
    public void deleteTest() throws Exception {
        Long id = 1L;
        courseService.delete(id);
        verify(courseRepository, times(1)).deleteById(id);
    }

    @Test
    public void getAllTest() throws Exception {
        List<Course> courses = CourseGenerator.courses();
        given(courseRepository.findAll()).willReturn(courses);

        List<Course> coursesExpected = courseService.getAll();

        assertEquals(coursesExpected, courses);
    }

    @Test
    public void getByIdTest() throws Exception {
        Long id = 1L;
        Course course = CourseGenerator.course(id);

        given(courseRepository.findById(id)).willReturn(Optional.of(course));

        Optional<Course> courseExpected = courseService.getById(id);

        assertThat(courseExpected).isNotNull();

        assertEquals(courseExpected, Optional.of(course));
    }
}
