package com.esiazy.dynamic.sql.source;

import com.esiazy.dynamic.sql.cache.ContextCache;
import com.esiazy.dynamic.sql.cache.DynamicCacheConfig;
import com.esiazy.dynamic.sql.executor.BaseExecutor;
import com.esiazy.dynamic.sql.executor.ExecuteContext;
import com.esiazy.dynamic.sql.executor.Executor;
import com.esiazy.dynamic.sql.executor.result.MetaMapResultHandler;
import com.esiazy.dynamic.sql.executor.result.ResultHandler;
import com.esiazy.dynamic.sql.executor.statment.PreparedStatementHandler;
import com.esiazy.dynamic.sql.executor.statment.StatementHandler;
import com.esiazy.dynamic.sql.plugin.Interceptor;
import com.esiazy.dynamic.sql.plugin.InterceptorChain;
import com.esiazy.dynamic.sql.session.DefaultMapSqlSession;
import com.esiazy.dynamic.sql.session.MapSqlSession;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wxf
 * @date 2021/5/25 10:37
 */
public class Configuration {
    protected final InterceptorChain interceptorChain = new InterceptorChain();
    /**
     * 数据源
     */
    private final DataSource dataSource;
    /**
     * sql上下文cache
     */
    private final Map<String, ExecuteContext> contextCache;

    public Configuration(DataSource dataSource) {
        this.dataSource = dataSource;
        this.contextCache = new ConcurrentHashMap<>(32);
    }

    public ExecuteContext getContext(String contextId) {
        return contextCache.get(contextId);
    }

    public void addContext(String contextId, ExecuteContext executeContext) {
        contextCache.put(contextId, executeContext);
    }

    public void removeContext(String contextId) {
        contextCache.remove(contextId);
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void addInterceptor(Interceptor interceptor) {
        interceptorChain.addInterceptor(interceptor);
    }

    public Executor newExecutor() {
        Executor executor = new BaseExecutor(this);
        executor = (Executor) interceptorChain.pluginAll(executor);
        return executor;
    }

    public StatementHandler newStatementHandler(BoundSql boundSql) {
        StatementHandler statementHandler = new PreparedStatementHandler(boundSql, this);
        statementHandler = (StatementHandler) interceptorChain.pluginAll(statementHandler);
        return statementHandler;
    }

    public ResultHandler newResultHandler() {
        ResultHandler statementHandler = new MetaMapResultHandler();
        statementHandler = (ResultHandler) interceptorChain.pluginAll(statementHandler);
        return statementHandler;
    }

    public MapSqlSession newSqlSession(Executor executor) {
        MapSqlSession sqlSession = new DefaultMapSqlSession(this, executor);
        sqlSession = (MapSqlSession) interceptorChain.pluginAll(sqlSession);
        return sqlSession;
    }

    public ContextCache newCacheConfig(DynamicCacheConfig.Query query) {
        return new DynamicCacheConfig(this, query);
    }
}
