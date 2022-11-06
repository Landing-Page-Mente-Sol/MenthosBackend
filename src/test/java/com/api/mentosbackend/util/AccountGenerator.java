package com.api.mentosbackend.util;

import com.api.mentosbackend.entities.Account;

import java.util.ArrayList;
import java.util.List;

import static com.api.mentosbackend.util.UserGenerator.user;

public class AccountGenerator {

    public static Account account(Long accountId, Long userId) {
        return new Account(accountId, "username", "password", user(userId));
    }

    public static Account account(Long id) { return account(id, 1L); }

    public static Account account() { return account(1L); }

    public static List<Account> accounts(int size) {
        List<Account> accounts = new ArrayList<>();

        for (long i = 0L; i < size; ++i)
            accounts.add(account(i, i + 1));

        return accounts;
    }

    public static List<Account> accounts() { return accounts(10); }

}
