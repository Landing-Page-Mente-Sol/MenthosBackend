package com.api.mentosbackend.service;

import com.api.mentosbackend.entities.Account;
import com.api.mentosbackend.entities.Customer;

public interface IAccountService extends CrudService<Account, Long> {
    Account findAccountByUsername(String username) throws Exception;
    Account findAccountByUsernameAndPassword(String username, String password) throws Exception;
    Account findAccountByCustomer(Customer customer) throws Exception;
}
