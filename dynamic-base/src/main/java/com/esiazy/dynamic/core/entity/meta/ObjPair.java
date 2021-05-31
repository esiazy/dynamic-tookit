package com.esiazy.dynamic.core.entity.meta;

/**
 * @author wxf
 * @date 2021/5/28 14:17
 */
public final class ObjPair {
    private final String k;

    private final Object v;

    public ObjPair(String k, Object v) {
        this.k = k;
        this.v = v;
    }

    public static ObjPair of(String k, Object v) {
        return new ObjPair(k, v);
    }

    public String getK() {
        return k;
    }

    public Object getV() {
        return v;
    }
}
