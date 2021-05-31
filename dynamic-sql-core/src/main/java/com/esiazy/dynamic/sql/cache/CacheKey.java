package com.esiazy.dynamic.sql.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author wxf
 * @date 2021/5/24 17:45
 */
public class CacheKey {

    private final List<Object> updateList;

    private int count;

    public CacheKey() {
        updateList = new ArrayList<>(16);
        count = 0;
    }

    public void add(Object object) {
        count++;
        updateList.add(object);
    }

    public void add(Object... object) {
        for (Object o : object) {
            add(o);
        }
    }

    /**
     * 使用默认分隔符:进行元素分割生成缓存key
     *
     * @return key
     */
    @Override
    public String toString() {
        StringJoiner returnValue = new StringJoiner(":");
        updateList.stream().map(Object::toString).forEach(returnValue::add);
        return returnValue.toString();
    }
}
