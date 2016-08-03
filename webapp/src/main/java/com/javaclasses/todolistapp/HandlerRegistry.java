package com.javaclasses.todolistapp;

import com.javaclasses.todolistapp.controllers.Handler;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains map of handlers
 */
public class HandlerRegistry {

    private static HandlerRegistry handlerRegistry = new HandlerRegistry();
    private final Map<CompoundKey, Handler> registry;

    private HandlerRegistry() {
        registry = new HashMap<>();
    }

    public static HandlerRegistry getInstance() {
        return handlerRegistry;
    }

    public void registerHandler(CompoundKey key, Handler handler){
        registry.put(key, handler);
    }

    /*package*/ Handler getHandler(CompoundKey key) {
        return registry.get(key);
    }
}
