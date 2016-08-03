package com.javaclasses.todolistapp.storage;

import java.util.Collection;

/**
 * Repository public API
 */
public interface Repository<TypeId, Type> {

    Type getItem(TypeId id);

    TypeId add(Type item);

    Collection<Type> getAll();

}
