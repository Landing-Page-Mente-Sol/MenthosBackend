package com.api.mentosbackend.util;

public class SetId<Entity extends ISetId> implements SetValue<Entity, Long> {
    @Override
    public void call(Entity entity, Long id) {
        entity.setId(id);
    }
}
