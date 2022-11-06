package com.api.mentosbackend.service;

import com.api.mentosbackend.entities.User;

import java.util.List;
import java.util.Optional;

public interface IUserService extends CrudService<User, Long> {
    List<User> findUsersByCycle(int cycle) throws Exception;
    List<User> findUsersByPointsGreaterThanEqual(Long points) throws Exception;
    List<User> findTop100ByOrderByPointsDesc() throws Exception;
    List<User> findUsersByUserType(String userType) throws Exception;
    //@Query("select b from Booking b where b.checkingDate=:checkingDate and b.checkoutDate=:checkoutDate")

    Optional<User> findUserByEmail(String email) throws Exception;

}
