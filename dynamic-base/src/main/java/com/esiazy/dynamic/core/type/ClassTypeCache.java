package com.esiazy.dynamic.core.type;

import java.lang.reflect.Field;

public class ClassTypeCache {
    private Field field;
    private Class<?> type;
    private String name;
    private boolean accessible;

    public ClassTypeCache(Field field, Class<?> type, String name, boolean accessible) {
        this.field = field;
        this.type = type;
        this.name = name;
        this.accessible = accessible;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAccessible() {
        return accessible;
    }

    public void setAccessible(boolean accessible) {
        this.accessible = accessible;
    }
}