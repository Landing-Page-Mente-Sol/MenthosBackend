package com.api.mentosbackend.service.impl;

import com.api.mentosbackend.entities.Account;
import com.api.mentosbackend.entities.User;
import com.api.mentosbackend.repository.IAccountRepository;
import com.api.mentosbackend.service.IAccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service//indicamos que es un sevicio
@Transactional(readOnly = true)//todos lo servicios solo seran de lectura
public class AccountServiceImpl extends CrudServiceImpl<Account, Long> implements IAccountService {

    private final IAccountRepository accountRepository;//final-> para que sea solo de lectura

    public AccountServiceImpl(IAccountRepository accountRepository) {
        super(accountRepository);
        this.accountRepository = accountRepository;
    }

    @Override
    public Account findAccountByUsername(String username) throws Exception { return this.accountRepository.findAccountByUsername(username); }

    @Override
    public Account findAccountByUsernameAndPassword(String username, String password) throws Exception {
        return this.accountRepository.findAccountByUsernameAndPassword(username, password);
    }

    @Override
    public Account findAccountByUser(User user) throws Exception {
        return this.accountRepository.findAccountByUser(user);
    }
}
