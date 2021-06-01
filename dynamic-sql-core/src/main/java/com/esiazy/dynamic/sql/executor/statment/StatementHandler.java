package com.esiazy.dynamic.sql.executor.statment;

import com.esiazy.dynamic.sql.executor.result.ResultHandler;
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

    /**
     * update
     *
     * @param statement stmt
     * @return 更新条数
     * @throws SQLException sql异常
     */
    int update(Statement statement)
            throws SQLException;

    /**
     * 查询
     *
     * @param statement stmt
     * @return 集合
     * @throws SQLException sql异常
     */
    <E> List<E> query(Statement statement, ResultHandler resultHandler) throws SQLException;

    /**
     * 获取执行sql对象
     *
     * @return {@link BoundSql}
     */
    BoundSql getBoundSql();

    /**
     * 解析stmt
     *
     * @param connection 连接
     * @return stmt
     * @throws SQLException sql异常
     */
    Statement prepare(Connection connection)
            throws SQLException;

    /**
     * 初始化stmt
     *
     * @param connection 连接
     * @param sql        sql
     * @return stmt
     * @throws SQLException sql异常
     */
    Statement instantiateStatement(Connection connection, String sql) throws SQLException;
}
