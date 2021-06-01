package com.esiazy.dynamic.core.type;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author wxf
 * @date 2021/6/1 4:00 下午
 */
public class TypeReference<E> {
    private final Class<?> clazz;
    protected Type type;

    protected TypeReference() {
        Type superClass = getClass().getGenericSuperclass();
        this.type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
        try {
            this.clazz = getClass().getClassLoader().loadClass(type.getTypeName());
        } catch (ClassNotFoundException classNotFoundException) {
            throw new RuntimeException("class Not found", classNotFoundException);
        }
    }

    public Type type() {
        return type;
    }

    public Class<?> getClazz() {
        return clazz;
    }
}
