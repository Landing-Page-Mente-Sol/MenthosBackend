package com.api.mentosbackend.util;

import com.api.mentosbackend.entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserGenerator {

    public static User user(){ return user(1L); }
    public static User user(Long id){
        return new User(id,"Yoimer", "Dávila", "student",
                "u20201b973@gmail.com", "U20201B973",
                "Ingeniería de Software", 5, 1000L);
    }
    public static List<User> users(int size) {
        List<User> usersList = new ArrayList<>();
        for(long i = 0L; i < size; ++i){ usersList.add(user(i)); }
        return usersList;
    }
    public static List<User> users() { return users(10); }
}
