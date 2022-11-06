package com.api.mentosbackend.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UtilFunctions {

    public static String objectAsJson(final Object object){
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception exception){
            throw new RuntimeException(exception);
        }
    }
}
