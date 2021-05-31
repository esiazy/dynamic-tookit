package com.esiazy.dynamic.sql.session;

import com.esiazy.dynamic.core.entity.meta.MetaHashMap;
import com.esiazy.dynamic.sql.exceptions.SqlRuntimeException;
import com.esiazy.dynamic.sql.exceptions.TooManyResultException;
import com.esiazy.dynamic.sql.executor.ExecuteContext;
import com.esiazy.dynamic.sql.executor.Executor;
import com.esiazy.dynamic.sql.source.Configuration;

import java.sql.SQLException;
import java.util.List;

/**
 * @author wxf
 * @date 2021/5/25 10:43
 */
public class DefaultMapSqlSession implements MapSqlSession {

    private final Configuration configuration;

    private final Executor executor;

    public DefaultMapSqlSession(Configuration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    @Override
    public MetaHashMap selectOne(String contextId, MetaHashMap parameter) {
        List<MetaHashMap> maps = this.selectList(contextId, parameter);
        if (maps.size() == 1) {
            return maps.get(0);
        } else if (maps.size() > 1) {
            throw new TooManyResultException("too many result for selectOne method. contextId:" + contextId);
        }
        return null;
    }

    @Override
    public MetaHashMap selectOne(String contextId) {
        return this.selectOne(contextId, null);
    }

    @Override
    public List<MetaHashMap> selectList(String contextId, MetaHashMap parameter) {
        try {
            ExecuteContext contextMap = configuration.getContext(contextId);
            if (contextMap == null) {
                throw new RuntimeException("none target sql context to found");
            }
            return executor.query(contextMap, parameter);
        } catch (SQLException throwable) {
            throw new SqlRuntimeException(throwable);
        }
    }

    @Override
    public int update(String contextId, MetaHashMap parameter) {
        ExecuteContext contextMap = configuration.getContext(contextId);
        try {
            return executor.update(contextMap, parameter);
        } catch (SQLException troubles) {
            throw new SqlRuntimeException(troubles);
        }
    }
}
