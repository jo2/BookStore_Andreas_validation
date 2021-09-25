package com.adesso.commentator.bookstore.error;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EntityWithIdNotFondException extends IllegalArgumentException {

    final Class<?> entityType;
    final Long id;

    public EntityWithIdNotFondException(Class<?> entityType, Long id, String message) {
        super(message);
        this.entityType = entityType;
        this.id = id;
    }
}
