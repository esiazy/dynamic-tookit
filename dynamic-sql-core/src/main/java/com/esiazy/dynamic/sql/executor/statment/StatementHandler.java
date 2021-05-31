package com.esiazy.dynamic.sql.executor.statment;

import com.esiazy.dynamic.core.entity.meta.MetaHashMap;
import com.esiazy.dynamic.sql.source.BoundSql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author wxf
 * @date 2021/5/28 15:01
 */
public interface StatementHandler {

    int update(Statement statement)
            throws SQLException;

    List<MetaHashMap> query(Statement statement) throws SQLException;

    BoundSql getBoundSql();

    Statement prepare(Connection connection)
            throws SQLException;

    Statement instantiateStatement(Connection connection, String sql) throws SQLException;
}
