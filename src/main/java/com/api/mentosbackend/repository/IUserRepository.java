package com.api.mentosbackend.repository;

import com.api.mentosbackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {

    List<User> findUsersByCycle(int cycle);
    List<User> findUsersByPointsGreaterThanEqual(Long points);
    List<User> findTop100ByOrderByPointsDesc();
    //si tiene una S el objeto de retorno es una S
    List<User> findUsersByUserType(String userType);
    //si no tiene una S se tiene que poner Optional
    Optional<User> findUserByEmail(String email);
}
