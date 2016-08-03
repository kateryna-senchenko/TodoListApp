package com.javaclasses.todolistapp.storage;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Abstract class for InMemoryRepository
 */
public abstract class InMemoryRepository<TypeId, Type> implements Repository<TypeId, Type>{

    private Map<TypeId, Type> entities = new ConcurrentHashMap<>();

    @Override
    public Type getItem(TypeId typeId) {
        return entities.get(typeId);
    }

    @Override
    public TypeId add(Type item) {
        TypeId typeId = generateId();
        entities.put(typeId, item);
        return typeId;
    }

    @Override
    public Collection<Type> getAll() {
        return entities.values();
    }

    @Override
    public Type remove(TypeId typeId) {
        return entities.remove(typeId);
    }

    public abstract TypeId generateId();
}
