package com.api.mentosbackend.service;

import com.api.mentosbackend.entities.User;
import com.api.mentosbackend.repository.IUserRepository;
import com.api.mentosbackend.service.impl.UserServiceImpl;
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
public class UserServiceImplTest {

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void saveTest(){
        User user = UserGenerator.user();
        given(userRepository.save(user)).willReturn(user);

        User savedUser = null;
        try {
            savedUser = userService.save(user);
        } catch (Exception e){
            e.printStackTrace();
        }

        assertThat(savedUser).isNotNull();
        assertEquals(user, savedUser);

    }

    @Test
    public void deleteTest() throws Exception {
        Long id = 1L;
        userService.delete(id);
        verify(userRepository, times(1)).deleteById(id);
    }

    @Test
    public void getAllTest() throws Exception {
        List<User> users = UserGenerator.users();
        given(userRepository.findAll()).willReturn(users);
        List<User> usersExpected = userService.getAll();
        assertEquals(usersExpected, users);
    }

    @Test
    public void getByIdTest() throws Exception {
        Long id = 1L;
        User user = UserGenerator.user(id);
        given(userRepository.findById(id)).willReturn(Optional.of(user));

        Optional<User> userExpected = userService.getById(id);

        assertThat(userExpected).isNotNull();
        assertEquals(userExpected, Optional.of(user));
    }

    @Test
    public void findUserByCycleTest() throws Exception {
        int cycle = 5;
        int size = 10;
        List<User> users = UserGenerator.users(size);
        users.forEach(user -> user.setCycle(cycle));

        given(userRepository.findUsersByCycle(cycle)).willReturn(users);

        List<User> usersExpected = userService.findUsersByCycle(cycle);

        assertEquals(usersExpected, users);
    }

    @Test
    public void findUsersByPointsGreaterThanEqualTest() throws Exception {
        Long points = 1500L;
        int size = 10;
        List<User> users = UserGenerator.users(size);
        users.forEach(u -> u.setPoints(points));

        given(userRepository.findUsersByPointsGreaterThanEqual(points)).willReturn(users);

        List<User> usersExpected = userService.findUsersByPointsGreaterThanEqual(points);

        assertEquals(usersExpected, users);
    }

    @Test
    public void findTop100ByOrderByPointsDescTest() throws Exception {
        List<User> users = UserGenerator.users();

        given(userRepository.findTop100ByOrderByPointsDesc()).willReturn(users);

        List<User> usersExpected = userService.findTop100ByOrderByPointsDesc();

        assertEquals(usersExpected, users);
    }

    @Test
    public void findUsersByUserType() throws Exception {
        List<User> users = UserGenerator.users();
        String userType = "tutor";
        users.forEach(u -> u.setUserType(userType));

        given(userRepository.findUsersByUserType(userType)).willReturn(users);

        List<User> usersExpected = userService.findUsersByUserType(userType);

        assertEquals(usersExpected, users);
    }
}
