package com.esiazy.dynamic.sql.source.sqlsource;

import com.esiazy.dynamic.sql.source.BoundSql;
import com.esiazy.dynamic.sql.source.SqlSource;

import java.util.List;
import java.util.Map;

/**
 * @author wxf
 * @date 2021/5/28 10:45
 */
public final class StaticSqlSource implements SqlSource {
    private final String sql;

    private final List<String> bindsParamsNames;

    public StaticSqlSource(String sql, List<String> bindsParamsNames) {
        this.sql = sql;
        this.bindsParamsNames = bindsParamsNames;
    }

    @Override
    public BoundSql getBoundSql(Map<String, Object> param) {
        return new BoundSql(bindsParamsNames, sql, param);
    }
}
