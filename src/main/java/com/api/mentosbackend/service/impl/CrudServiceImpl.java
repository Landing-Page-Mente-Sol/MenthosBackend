package com.api.mentosbackend.service.impl;

import com.api.mentosbackend.service.CrudService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class CrudServiceImpl<Entity, Id> implements CrudService<Entity, Id> {

    private final JpaRepository<Entity, Id> repository;

    public CrudServiceImpl(JpaRepository<Entity, Id> repository) { this.repository = repository; }

    @Override
    @Transactional//hará cambios en la base de datos
    public Entity save(Entity entity) throws Exception { return this.repository.save(entity); }

    @Override
    @Transactional//hará cambios en la base de datos
    public void delete(Id id) throws Exception { this.repository.deleteById(id); }

    @Override
    public List<Entity> getAll() throws Exception { return this.repository.findAll(); }

    @Override
    public Optional<Entity> getById(Id id) throws Exception { return this.repository.findById(id); }
}
