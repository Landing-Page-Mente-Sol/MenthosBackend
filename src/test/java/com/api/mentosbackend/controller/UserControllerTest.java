package com.api.mentosbackend.controller;

import com.api.mentosbackend.entities.User;
import com.api.mentosbackend.service.impl.UserServiceImpl;
import com.api.mentosbackend.util.UtilFunctions;
import com.api.mentosbackend.util.UserGenerator;
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

@WebMvcTest(controllers = UserController.class)
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserServiceImpl userService;

    private List<User> users;

    private final String basePath = "/api/v1/users";

    @BeforeEach
    void setup() { this.users = UserGenerator.users(); }

    @Test
    void findAllUsersTest() throws Exception {
        given(userService.getAll()).willReturn(users);
        mockMvc.perform(get(this.basePath)).andExpect(status().isOk());
    }

    @Test
    void findUserByIdTest() throws Exception {
        Long id = 1L;
        User user = UserGenerator.user(id);
        given(userService.getById(id)).willReturn(Optional.of(user));

        mockMvc.perform(get(this.basePath+"/{id}", id)).andExpect(status().isOk());
    }

    @Test
    void insertUserTest() throws Exception {
        User user = UserGenerator.user();
        mockMvc.perform(post(this.basePath)
                        .content(UtilFunctions.objectAsJson(user))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    @Test
    void updateUserTest() throws Exception {
        Long id = 1L;
        User user = UserGenerator.user(id);
        given(userService.getById(id)).willReturn(Optional.of(user));

        mockMvc.perform(put(this.basePath + "/{id}", id)
                        .content(UtilFunctions.objectAsJson(user))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void deleteUserTest() throws Exception {
        Long id = 1L;
        User user = UserGenerator.user(id);

        given(userService.getById(id)).willReturn(Optional.of(user));

        mockMvc.perform(delete(this.basePath+"/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void findUsersByCycleTest() throws Exception {
        int cycle = 8;
        users.forEach(user -> user.setCycle(cycle));

        given(userService.findUsersByCycle(cycle)).willReturn(users);

        mockMvc.perform(get(this.basePath + "/search/users/cycle/{cycle}", cycle))
                .andExpect(status().isOk());
    }

    @Test
    void findUsersByPointsGreaterThanEqualTest() throws Exception {
        Long points = 1500L;
        users.forEach(user -> user.setPoints(points));

        given(userService.findUsersByPointsGreaterThanEqual(points)).willReturn(users);

        mockMvc.perform(get(this.basePath + "/search/user/points/gte/{points}", points))
                .andExpect(status().isOk());

    }

    @Test
    void findTop100ByOrderByPointsDescTest() throws Exception {
        given(userService.findTop100ByOrderByPointsDesc()).willReturn(users);

        mockMvc.perform(get(this.basePath + "/search/user/points/top/100/desc"))
                .andExpect(status().isOk());
    }

    @Test
    void findUsersByUserTypeTest() throws Exception {
        String type = "tutor";
        users.forEach(user -> user.setUserType(type));

        given(userService.findUsersByUserType(type)).willReturn(users);

        mockMvc.perform(get(this.basePath + "/search/user/type/{userType}", type))
                .andExpect(status().isOk());
    }
}
