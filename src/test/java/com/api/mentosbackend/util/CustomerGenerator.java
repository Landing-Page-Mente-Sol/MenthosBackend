package com.api.mentosbackend.util;

import com.api.mentosbackend.entities.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerGenerator {

    public static Customer customer(){ return customer(1L); }
    public static Customer customer(Long id){
        return new Customer(id,"Yoimer", "Dávila", "student",
                "u20201b973@gmail.com", "U20201B973",
                "Ingeniería de Software", 5, 1000L);
    }
    public static List<Customer> customers(int size) {
        List<Customer> customerList = new ArrayList<>();
        for(long i = 0L; i < size; ++i){ customerList.add(customer(i)); }
        return customerList;
    }
    public static List<Customer> customers() { return customers(10); }
}
