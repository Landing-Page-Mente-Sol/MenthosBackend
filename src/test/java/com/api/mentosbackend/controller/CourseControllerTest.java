package com.api.mentosbackend.controller;
import com.api.mentosbackend.entities.Course;
import com.api.mentosbackend.service.impl.CourseServiceImpl;
import com.api.mentosbackend.util.CourseGenerator;
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

@WebMvcTest(controllers = CourseController.class)
@ActiveProfiles("test")
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseServiceImpl courseService;

    private List<Course> courses;

    private final String basePath = "/api/v1/courses";

    @BeforeEach
    void setup() { this.courses = CourseGenerator.courses(); }

    @Test
    void findAllCoursesTest() throws Exception {
        given(courseService.getAll()).willReturn(courses);

        mockMvc.perform(get(this.basePath)).andExpect(status().isOk());
    }

    @Test
    void findCourseByIdTest() throws Exception {
        Long id = 1L;
        Course course = CourseGenerator.course(id);

        given(courseService.getById(id)).willReturn(Optional.of(course));

        mockMvc.perform(get(this.basePath + "/{id}", id)).andExpect(status().isOk());
    }

    @Test
    void insertCourseTest() throws Exception {
        Course course = CourseGenerator.course();

        mockMvc.perform(post(this.basePath)
                        .content(UtilFunctions.objectAsJson(course))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    @Test
    void  updateCourseTest() throws Exception {
        Long id = 1L;
        Course course = CourseGenerator.course(id);

        given(courseService.getById(id)).willReturn(Optional.of(course));

        mockMvc.perform(put(this.basePath + "/{id}", id)
                        .content(UtilFunctions.objectAsJson(course))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCourseTest() throws Exception {
        Long id = 1L;
        Course course = CourseGenerator.course(id);

        given(courseService.getById(id)).willReturn(Optional.of(course));

        mockMvc.perform(delete(this.basePath + "/{id}", id))
                .andExpect(status().isOk());
    }
}
