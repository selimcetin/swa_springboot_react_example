package com.hsesslingen.jodel.exceptions;

public class EntityIdNotFoundException extends RuntimeException {
    public EntityIdNotFoundException(Long id, String entityName) {
        super(String.format("Could not find %s with ID %d", entityName, id));
    }

    public EntityIdNotFoundException(String id, String entityName) {
        super(String.format("Could not find %s with ID %s", entityName, id));
    }
}
