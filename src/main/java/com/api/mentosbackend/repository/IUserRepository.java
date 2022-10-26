package com.api.mentosbackend.repository;

import com.api.mentosbackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUserRepository extends JpaRepository<User, Long> {

    List<User> findUsersByCycle(int cycle);
    List<User> findUsersByPointsGreaterThanEqual(Long points);
    List<User> findTop100ByOrderByPointsDesc();
}
