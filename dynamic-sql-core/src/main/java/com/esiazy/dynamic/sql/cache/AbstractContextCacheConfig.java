package com.esiazy.dynamic.sql.cache;

import com.esiazy.dynamic.sql.exceptions.TargetRoutingException;
import com.esiazy.dynamic.sql.executor.ExecuteContext;
import com.esiazy.dynamic.sql.source.Configuration;

/**
 * 该类默认为调用时进行加载
 *
 * @author wxf
 * @date 2021/5/26 11:29
 */
public abstract class AbstractContextCacheConfig implements ContextCache {
    private final Configuration configuration;

    public AbstractContextCacheConfig(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void checkAndUpdate(String contextId) {
        //调用子类实现
        long updateTime = getUpdateTime(contextId);
        ExecuteContext context = configuration.getContext(contextId);
        context = nullToSelect(contextId, context);
        if (updateTime != context.getLastUpdateTime().getTime()) {
            configuration.addContext(contextId, getOne(contextId));
        }
    }

    private ExecuteContext nullToSelect(String contextId, ExecuteContext context) {
        if (context == null) {
            context = getOne(contextId);
            if (context == null) {
                throw new TargetRoutingException("none sql target please check table row.");
            }
            configuration.addContext(contextId, context);
        }
        return context;
    }
}
