package com.javaclasses.todolistapp.storage;

import java.util.Collection;

/**
 * Repository public API
 */
public interface Repository<TypeId, Type> {

    /**
     * Provides access to stored item
     * @param id - unique id
     * @return stored object
     */
    Type getItem(TypeId id);

    /**
     * Adds item to the repository
     * @param item - item to be added
     * @return unique item id
     */
    TypeId add(Type item);

    /**
     * Provides access to all stored items
     * @return collection of items
     */
    Collection<Type> getAll();

    /**
     * Removes item from repository
     * @param id - unique item id
     * @return removed object
     */
    Type remove(TypeId id);

}
