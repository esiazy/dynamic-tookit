package com.esiazy.dynamic.sql.source.sqlsource;

import java.util.Map;
import java.util.StringJoiner;

/**
 * @author Esadmin
 * @date 2021/5/24 16:07
 */
public class DynamicSqlContext {
    private final Map<String, Object> params;

    private final StringJoiner sqlBuilder;

    public DynamicSqlContext(Map<String, Object> param) {
        this.params = param;
        sqlBuilder = new StringJoiner("");
    }


    public void appendSql(String sql) {
        sqlBuilder.add(sql);
    }

    public String getSql() {
        return sqlBuilder.toString().trim();
    }

    public Map<String, Object> getParams() {
        return params;
    }
}
