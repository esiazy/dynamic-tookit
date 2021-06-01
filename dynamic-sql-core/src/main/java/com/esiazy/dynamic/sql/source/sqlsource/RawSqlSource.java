package com.esiazy.dynamic.sql.source.sqlsource;

import com.esiazy.dynamic.core.entity.meta.MetaHashMap;
import com.esiazy.dynamic.sql.script.xml.node.SqlNode;
import com.esiazy.dynamic.sql.source.BoundSql;
import com.esiazy.dynamic.sql.source.SqlSource;
import com.esiazy.dynamic.sql.util.parser.SqlSourceParser;

import java.util.Map;

/**
 * @author wxf
 * @date 2021/5/28 8:37
 */
public final class RawSqlSource implements SqlSource {
    private final SqlSource sqlSource;

    public RawSqlSource(SqlNode sqlNode) {
        String sql = getSql(sqlNode);
        SqlSourceParser parser = new SqlSourceParser();
        this.sqlSource = parser.parseSharp(sql, new MetaHashMap());
    }

    private static String getSql(SqlNode rootSqlNode) {
        DynamicSqlContext context = new DynamicSqlContext(null);
        rootSqlNode.build(context);
        return context.getSql();
    }

    @Override
    public BoundSql getBoundSql(Map<String, Object> param) {
        return sqlSource.getBoundSql(param);
    }
}
