package com.esiazy.dynamic.core.entity.meta;

import com.esiazy.dynamic.core.util.TypeUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author wxf
 * @date 2021/5/28 14:05
 */
public class MetaHashMap implements Map<String, Object>, Serializable {
    private final Map<String, Object> map;

    public MetaHashMap() {
        this(16);
    }

    public MetaHashMap(int initialCapacity) {
        this(new HashMap<>(initialCapacity));
    }

    public MetaHashMap(Map<String, Object> map) {
        this.map = map;
    }

    public static MetaHashMap of(ObjPair... objPairs) {
        MetaHashMap map = new MetaHashMap(16);
        for (ObjPair objPair : objPairs) {
            map.put(objPair.getK(), objPair.getV());
        }
        return map;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return map.get(key);
    }

    @Override
    public Object put(String key, Object value) {
        return map.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<String> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<Object> values() {
        return map.values();
    }

    @Override
    public Set<Map.Entry<String, Object>> entrySet() {
        return map.entrySet();
    }

    public MetaHashMap putRe(String key, Object value) {
        this.put(key, value);
        return this;
    }

    public Map<String, Object> toMap() {
        return map;
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

    @SuppressWarnings("all")
    public MetaHashMap getMetaMap(String key) {
        Object value = map.get(key);
        if (value instanceof Map) {
            return new MetaHashMap((Map) value);
        }
        return null;
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
