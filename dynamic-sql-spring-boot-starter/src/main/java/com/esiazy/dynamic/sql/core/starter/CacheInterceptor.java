package com.esiazy.dynamic.sql.core.starter;

import com.esiazy.dynamic.core.type.TypeReference;
import com.esiazy.dynamic.sql.cache.ContextCache;
import com.esiazy.dynamic.sql.plugin.Interceptor;
import com.esiazy.dynamic.sql.plugin.Intercepts;
import com.esiazy.dynamic.sql.plugin.Plugin;
import com.esiazy.dynamic.sql.plugin.Signature;
import com.esiazy.dynamic.sql.session.MapSqlSession;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * sql缓存拦截
 *
 * @author wxf
 * @date 2021/5/29 9:24
 */
@Intercepts({
        @Signature(type = MapSqlSession.class, method = "selectOne", args = {String.class, Map.class}),
        @Signature(type = MapSqlSession.class, method = "selectOne", args = {String.class}),
        @Signature(type = MapSqlSession.class, method = "selectList", args = {String.class, Map.class}),
        @Signature(type = MapSqlSession.class, method = "selectList", args = {String.class, Map.class, TypeReference.class}),
        @Signature(type = MapSqlSession.class, method = "update", args = {String.class, Map.class}),
})
public class CacheInterceptor implements Interceptor {
    private final ContextCache contextCache;

    public CacheInterceptor(ContextCache contextCache) {
        this.contextCache = contextCache;
    }

    @Override
    public Object intercept(Object proxy, Method method, Object[] args) throws Throwable {
        Object arg = args[0];
        contextCache.checkAndUpdate(arg.toString());
        return method.invoke(proxy, args);
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
}