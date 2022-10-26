package com.api.mentosbackend.controller.common;

import com.api.mentosbackend.service.CrudService;
import com.api.mentosbackend.util.SetValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class RelatedCrudController<Entity, Id, RelatedEntity, RelatedId> extends CrudController<Entity, Id> {

    protected final CrudService<RelatedEntity, RelatedId> relatedCrudService;

    public RelatedCrudController(CrudService<Entity, Id> simpleCrudService, CrudService<RelatedEntity, RelatedId> relatedCrudService) {
        super(simpleCrudService);
        this.relatedCrudService = relatedCrudService;
    }

    protected ResponseEntity<Entity> insert(RelatedId relatedId, Entity entity, SetValue<Entity, RelatedEntity> setValue){
        try {
            Optional<RelatedEntity> relatedEntity = this.relatedCrudService.getById(relatedId);
            if(relatedEntity.isPresent()){
                setValue.call(entity, relatedEntity.get());
                Entity entityNew = this.simpleCrudService.save(entity);
                return ResponseEntity.status(HttpStatus.CREATED).body(entityNew);
            }
            return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
        } catch (Exception ignored){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
