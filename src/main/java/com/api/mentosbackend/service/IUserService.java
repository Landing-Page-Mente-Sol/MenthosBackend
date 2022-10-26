package com.api.mentosbackend.service;

import com.api.mentosbackend.entities.User;

import java.util.List;

public interface IUserService extends CrudService<User, Long> {
    List<User> findUsersByCycle(int cycle) throws Exception;
    List<User> findUsersByPointsGreaterThanEqual(Long points) throws Exception;
    List<User> findTop100ByOrderByPointsDesc() throws Exception;
}
