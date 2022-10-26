package com.api.mentosbackend.util;

import com.api.mentosbackend.entities.Account;
import com.api.mentosbackend.entities.User;

public class AssignAccount implements SetValue<Account, User> {
    @Override
    public void call(Account account, User user) { account.setUser(user); }
}
