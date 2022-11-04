package com.api.mentosbackend.service;
import com.api.mentosbackend.entities.Account;
import com.api.mentosbackend.entities.User;
import com.api.mentosbackend.repository.IAccountRepository;
import com.api.mentosbackend.service.impl.AccountServiceImpl;
import com.api.mentosbackend.util.AccountGenerator;
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
public class AccountServiceImplTest {
    @Mock
    private IAccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    public void saveTest() throws Exception {
        Account account = AccountGenerator.account();

        given(accountRepository.save(account)).willReturn(account);

        Account accountSaved = accountService.save(account);

        assertThat(accountSaved).isNotNull();
        assertEquals(accountSaved, account);
    }

    @Test
    public void deleteTest() throws Exception {
        Long id = 1L;
        accountService.delete(id);
        verify(accountRepository, times(1)).deleteById(id);
    }

    @Test
    public void getAllTest() throws Exception {
        List<Account> accounts = AccountGenerator.accounts();

        given(accountRepository.findAll()).willReturn(accounts);

        List<Account> accountsExpected = accountService.getAll();

        assertEquals(accountsExpected, accounts);
    }

    @Test
    public void getByIdTest() throws Exception {
        Long id = 1L;
        Account account = AccountGenerator.account(id);

        given(accountRepository.findById(id)).willReturn(Optional.of(account));

        Optional<Account> accountExpected = accountService.getById(id);

        assertThat(accountExpected).isNotNull();
        assertEquals(accountExpected, Optional.of(account));
    }

    @Test
    public void findAccountByUsernameTest() throws Exception {
        String username = "username";
        Account account = AccountGenerator.account();
        account.setUsername(username);

        given(accountRepository.findAccountByUsername(username)).willReturn(account);

        Account accountExpected = accountService.findAccountByUsername(username);

        assertThat(accountExpected).isNotNull();
        assertEquals(accountExpected, account);
    }

    @Test
    public void findAccountByUsernameAndPasswordTest() throws Exception {
        String username = "username";
        String password = "password";
        Account account = AccountGenerator.account();
        account.setUsername(username);
        account.setPassword(password);

        given(accountRepository.findAccountByUsernameAndPassword(username, password)).willReturn(account);

        Account accountExpected = accountService.findAccountByUsernameAndPassword(username, password);

        assertThat(accountExpected).isNotNull();
        assertEquals(accountExpected, account);
    }

    @Test
    public void findAccountByUserTest() throws Exception {
        User user = UserGenerator.user();
        Account account = AccountGenerator.account();
        account.setUser(user);

        given(accountRepository.findAccountByUser(user)).willReturn(account);

        Account accountExpected = accountService.findAccountByUser(user);

        assertThat(accountExpected).isNotNull();
        assertEquals(accountExpected, account);
    }
}
