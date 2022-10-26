package com.api.mentosbackend.repository;

import com.api.mentosbackend.entities.Account;
import com.api.mentosbackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IAccountRepository extends JpaRepository<Account, Long> {
    Account findAccountByUsername(String username);
    Account findAccountByUsernameAndPassword(String username, String password);
    Account findAccountByUser(User user);
}
