package com.api.mentosbackend.service;

import java.util.List;
import java.util.Optional;

public interface CrudService<Type, Id> {
    Type save(Type type) throws Exception; //register or update
    void delete(Id id) throws Exception; //remove
    List<Type> getAll() throws Exception; //list of any object
    Optional<Type> getById(Id id) throws Exception; //get an object by id
}

