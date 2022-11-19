package com.api.mentosbackend.controller;
import com.api.mentosbackend.entities.Account;
import com.api.mentosbackend.entities.Customer;
import com.api.mentosbackend.service.impl.AccountServiceImpl;
import com.api.mentosbackend.service.impl.CustomerServiceImpl;
import com.api.mentosbackend.util.AccountGenerator;
import com.api.mentosbackend.util.CustomerGenerator;
import com.api.mentosbackend.util.UtilFunctions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AccountController.class)
@ActiveProfiles("test")
public class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountServiceImpl accountService;
    @MockBean
    private CustomerServiceImpl customerService;

    private List<Account> accounts;
    private final String basePath = "/api/v1/accounts";

    @BeforeEach
    void setup() { this.accounts = AccountGenerator.accounts(); }

    @Test
    void findAllAccountsTest() throws Exception {
        given(accountService.getAll()).willReturn(accounts);

        mockMvc.perform(get(this.basePath)).andExpect(status().isOk());
    }

    @Test
    void findAccountByIdTest() throws Exception {
        Long id = 1L;
        Account account = AccountGenerator.account(id);

        given(accountService.getById(id)).willReturn(Optional.of(account));
        mockMvc.perform(get(this.basePath + "/{id}", id)).andExpect(status().isOk());
    }

    @Test
    void insertAccountTest() throws Exception {
        Account account = AccountGenerator.account();
        Long id = 1L;
        Customer customer = CustomerGenerator.customer(id);

        given(customerService.getById(id)).willReturn(Optional.of(customer));

        mockMvc.perform(post(this.basePath + "/{customerId}", id)
                        .content(UtilFunctions.objectAsJson(account))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    @Test
    void updateAccountTest() throws Exception {
        Long id = 1L;
        Account account = AccountGenerator.account(id);


        given(accountService.getById(id)).willReturn(Optional.of(account));

        mockMvc.perform(put(this.basePath + "/{id}", id)
                        .content(UtilFunctions.objectAsJson(account))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void deleteAccountTest() throws Exception {
        Long id = 1L;
        Account account = AccountGenerator.account(id);

        given(accountService.getById(id)).willReturn(Optional.of(account));

        mockMvc.perform(delete(this.basePath + "/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void findAccountByUsernameTest() throws Exception {
        String username = "username";
        Account account = AccountGenerator.account();
        account.setUsername(username);

        given(accountService.findAccountByUsername(username)).willReturn(account);

        mockMvc.perform(get(this.basePath + "/search/username/{username}", username))
                .andExpect(status().isOk());
    }

    @Test
    void findAccountByUsernameAndPasswordTest() throws Exception {
        String username = "username";
        String password = "password";

        Account account = AccountGenerator.account();

        account.setUsername(username);
        account.setPassword(password);

        given(accountService.findAccountByUsernameAndPassword(username, password)).willReturn(account);

        mockMvc.perform(get(this.basePath + "/search/username/{username}/password/{password}", username, password))
                .andExpect(status().isOk());
    }

    @Test
    void findAccountByUserTest() throws Exception {
        Long id = 1L;
        Customer customer = CustomerGenerator.customer(id);

        Account account = AccountGenerator.account();
        given(accountService.findAccountByCustomer(customer)).willReturn(account);
        given(customerService.getById(id)).willReturn(Optional.of(customer));

        mockMvc.perform(get(this.basePath + "/search/customer/{customerId}", id))
                .andExpect(status().isOk());
    }
}
