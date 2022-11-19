package com.api.mentosbackend.util;

import com.api.mentosbackend.entities.Account;
import com.api.mentosbackend.entities.Customer;

public class AssignAccount implements SetValue<Account, Customer> {
    @Override
    public void call(Account account, Customer customer) { account.setCustomer(customer); }
}
