package com.api.mentosbackend.controller.common;

import com.api.mentosbackend.service.CrudService;
import com.api.mentosbackend.util.SetValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public class CrudController<Entity, Id> {
    protected final CrudService<Entity, Id> simpleCrudService;
    protected CrudController(CrudService<Entity, Id> crudService) {
        this.simpleCrudService = crudService;
    }
    protected ResponseEntity<List<Entity>> getAll() {//responseEntity -> es el json
        try {
            List<Entity> entities = this.simpleCrudService.getAll();
            if(entities.size() > 0)
                return new ResponseEntity<>(entities, HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ignored){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    protected ResponseEntity<Entity> getById(Id id) {
        try {
            Optional<Entity> entity = this.simpleCrudService.getById(id);//solo puede votar erro aqui
            if(!entity.isPresent())//si el objeto existe o no
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);//si en caso encontró el objeto
            return new ResponseEntity<>(entity.get(), HttpStatus.OK);//si en caso el objeto no existe
        } catch (Exception ignored){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    protected ResponseEntity<Entity> insert(Entity entity){
        try {
            Entity entityNew = this.simpleCrudService.save(entity);
            return ResponseEntity.status(HttpStatus.CREATED).body(entityNew);
            //return new ResponseEntity<>(entity.get(), HttpStatus.OK);// es igual a la linea anterior
        } catch (Exception ignored){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    protected ResponseEntity<Entity> update(Id id, Entity entity, SetValue<Entity, Id> setValue) {
        try {
            Optional<Entity> entityOld = simpleCrudService.getById(id);
            if(!entityOld.isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            setValue.call(entity, id);
            simpleCrudService.save(entity);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception ignored){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    protected ResponseEntity<Entity> delete(Id id) {
        try {
            Optional<Entity> entity = this.simpleCrudService.getById(id);
            if(!entity.isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            this.simpleCrudService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
            //return new ResponseEntity<>(entity.get(), HttpStatus.OK); //devuelve la entiendad borrada
        } catch (Exception ignored){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


