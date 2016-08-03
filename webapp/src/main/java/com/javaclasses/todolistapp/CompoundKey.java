package com.javaclasses.todolistapp;

/**
 * Encrypts url and method type
 */
public class CompoundKey {

    private final String compoundKey;

    public CompoundKey(String url, String method) {
        this.compoundKey = url + method;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompoundKey that = (CompoundKey) o;

        return compoundKey.equals(that.compoundKey);

    }

    @Override
    public int hashCode() {
        return compoundKey.hashCode();
    }
}
