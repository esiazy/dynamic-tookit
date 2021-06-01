package com.esiazy.dynamic.core.entity.meta;

import com.esiazy.dynamic.core.util.TypeUtil;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * @author wxf
 * @date 2021/5/28 14:05
 */
public class MetaHashMap extends HashMap<String, Object> {


    public MetaHashMap() {
        this(16);
    }

    public MetaHashMap(int initialCapacity) {
        super(initialCapacity);
    }

    public static MetaHashMap of(ObjPair... objPairs) {
        MetaHashMap map = new MetaHashMap(16);
        for (ObjPair objPair : objPairs) {
            map.put(objPair.getK(), objPair.getV());
        }
        return map;
    }

    public MetaHashMap putRe(String key, Object value) {
        this.put(key, value);
        return this;
    }

    public Boolean getBoolean(String key) {
        Object value = get(key);

        if (value == null) {
            return null;
        }

        return TypeUtil.castToBoolean(value);
    }

    public Integer getInteger(String key) {
        Object value = get(key);
        return TypeUtil.castToInt(value);
    }

    public Double getDouble(String key) {
        Object value = get(key);
        return TypeUtil.castToDouble(value);
    }

    public BigDecimal getBigDecimal(String key) {
        Object value = get(key);
        return TypeUtil.castToBigDecimal(value);
    }

    public String getString(String key) {
        Object value = get(key);
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    public Long getLong(String key) {
        Object value = get(key);
        return TypeUtil.castToLong(value);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
