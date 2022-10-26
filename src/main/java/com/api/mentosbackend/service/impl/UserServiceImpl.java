package com.api.mentosbackend.service.impl;

import com.api.mentosbackend.entities.User;
import com.api.mentosbackend.repository.IUserRepository;
import com.api.mentosbackend.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional(readOnly = true)
public class UserServiceImpl extends CrudServiceImpl<User, Long> implements IUserService {
    private final IUserRepository userRepository;

    public UserServiceImpl(IUserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findUsersByCycle(int cycle) throws Exception {
        return this.userRepository.findUsersByCycle(cycle);
    }

    @Override
    public List<User> findUsersByPointsGreaterThanEqual(Long points) throws Exception {
        return this.userRepository.findUsersByPointsGreaterThanEqual(points);
    }

    @Override
    public List<User> findTop100ByOrderByPointsDesc() throws Exception {
        return this.userRepository.findTop100ByOrderByPointsDesc();
    }
}
