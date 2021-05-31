package com.esiazy.dynamic.sql.session;


import com.esiazy.dynamic.core.entity.meta.MetaHashMap;

import java.util.List;

/**
 * SqlSession
 *
 * @author wxf
 * @date 2021/5/26 9:04
 */
public interface MapSqlSession {
    MetaHashMap selectOne(String contextId, MetaHashMap parameter);

    MetaHashMap selectOne(String contextId);

    List<MetaHashMap> selectList(String contextId, MetaHashMap parameter);

    int update(String contextId, MetaHashMap parameter);
}
