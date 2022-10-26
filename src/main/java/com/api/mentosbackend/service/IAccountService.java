package com.api.mentosbackend.service;

import com.api.mentosbackend.entities.Account;
import com.api.mentosbackend.entities.User;

public interface IAccountService extends CrudService<Account, Long> {
    Account findAccountByUsername(String username) throws Exception;
    Account findAccountByUsernameAndPassword(String username, String password) throws Exception;
    Account findAccountByUser(User user) throws Exception;
}
