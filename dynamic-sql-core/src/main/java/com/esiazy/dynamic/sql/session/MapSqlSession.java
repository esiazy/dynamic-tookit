package com.esiazy.dynamic.sql.session;


import com.esiazy.dynamic.core.entity.meta.MetaHashMap;
import com.esiazy.dynamic.core.type.TypeReference;

import java.util.List;
import java.util.Map;

/**
 * SqlSession
 *
 * @author wxf
 * @date 2021/5/26 9:04
 */
public interface MapSqlSession {
    MetaHashMap selectOne(String contextId, Map<String, Object> parameter);

    MetaHashMap selectOne(String contextId);

    List<MetaHashMap> selectList(String contextId, Map<String, Object> parameter);

    <E> List<E> selectList(String contextId, Map<String, Object> parameter, TypeReference<E> typeReference);

    int update(String contextId, Map<String, Object> parameter);
}
