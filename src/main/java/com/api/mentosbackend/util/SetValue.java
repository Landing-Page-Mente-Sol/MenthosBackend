package com.api.mentosbackend.util;

public interface SetValue<Entity, RelatedEntity> {
    void call(Entity entity, RelatedEntity relatedEntity);
}
