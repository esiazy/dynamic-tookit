package com.esiazy.dynamic.sql.core.starter;

import com.esiazy.dynamic.core.entity.meta.MetaHashMap;
import com.esiazy.dynamic.sql.cache.ContextCache;
import com.esiazy.dynamic.sql.plugin.Interceptor;
import com.esiazy.dynamic.sql.session.MapSqlSession;

import java.util.List;

/**
 * sql缓存拦截
 *
 * @author wxf
 * @date 2021/5/29 9:24
 */
public class CacheInterceptor implements Interceptor {
    private final ContextCache contextCache;

    public CacheInterceptor(ContextCache contextCache) {
        this.contextCache = contextCache;
    }

    @Override
    public Object plugin(Object target) {
        //拦截mapSession进行缓存
        if (target instanceof MapSqlSession) {
            return new MapSqlSession() {
                private final MapSqlSession mapSqlSession = (MapSqlSession) target;

                @Override
                public MetaHashMap selectOne(String contextId, MetaHashMap parameter) {
                    contextCache.checkAndUpdate(contextId);
                    return mapSqlSession.selectOne(contextId, parameter);
                }

                @Override
                public MetaHashMap selectOne(String contextId) {
                    contextCache.checkAndUpdate(contextId);
                    return mapSqlSession.selectOne(contextId);
                }

                @Override
                public List<MetaHashMap> selectList(String contextId, MetaHashMap parameter) {
                    contextCache.checkAndUpdate(contextId);
                    return mapSqlSession.selectList(contextId, parameter);
                }

                @Override
                public int update(String contextId, MetaHashMap parameter) {
                    contextCache.checkAndUpdate(contextId);
                    return mapSqlSession.update(contextId, parameter);
                }
            };
        }
        //放行剩下拦截
        return Interceptor.super.plugin(target);
    }
}
