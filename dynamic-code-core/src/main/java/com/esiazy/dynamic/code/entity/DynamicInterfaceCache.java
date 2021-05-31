package com.esiazy.dynamic.code.entity;

import java.util.Date;
import java.util.Map;

/**
 * @author wxf
 * @date 2021/5/29 10:58
 */
public class DynamicInterfaceCache {
    /**
     * target
     */
    private Object target;

    private Map<String, DynamicInterfaceMethodCache> methodCache;

    private Date lastUpdateTime;

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Map<String, DynamicInterfaceMethodCache> getMethodCache() {
        return methodCache;
    }

    public void setMethodCache(Map<String, DynamicInterfaceMethodCache> methodCache) {
        this.methodCache = methodCache;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
