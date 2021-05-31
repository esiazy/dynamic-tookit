package com.esiazy.dynamic.sql.executor.statment;

import com.esiazy.dynamic.core.entity.meta.MetaHashMap;
import com.esiazy.dynamic.sql.exceptions.ExecutorException;
import com.esiazy.dynamic.sql.executor.result.ResultHandler;
import com.esiazy.dynamic.sql.source.BoundSql;
import com.esiazy.dynamic.sql.source.Configuration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author wxf
 * @date 2021/5/28 15:10
 */
public class PreparedStatementHandler implements StatementHandler {
    protected BoundSql boundSql;

    protected Configuration configuration;

    public PreparedStatementHandler(BoundSql boundSql, Configuration configuration) {
        this.boundSql = boundSql;
        this.configuration = configuration;
    }

    @Override
    public BoundSql getBoundSql() {
        return boundSql;
    }

    @Override
    public Statement prepare(Connection connection) {
        Statement statement = null;
        try {
            statement = instantiateStatement(connection, boundSql.getSql());
            return statement;
        } catch (Exception e) {
            closeStatement(statement);
            throw new ExecutorException("Error preparing statement.  Cause: " + e, e);
        }
    }

    @Override
    public Statement instantiateStatement(Connection connection, String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    protected void closeStatement(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            //ignore
        }
    }

    @Override
    public int update(Statement statement) throws SQLException {
        PreparedStatement preparedStatement = (PreparedStatement) statement;
        preparedStatement.execute();
        return preparedStatement.getUpdateCount();
    }

    @Override
    public List<MetaHashMap> query(Statement statement) throws SQLException {
        PreparedStatement preparedStatement = (PreparedStatement) statement;
        preparedStatement.execute();
        ResultHandler handler = configuration.newResultHandler();
        return handler.handlerResultSet(statement);
    }

}
