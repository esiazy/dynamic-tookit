package com.esiazy.dynamic.sql.executor;

import com.esiazy.dynamic.core.entity.meta.MetaHashMap;
import com.esiazy.dynamic.sql.executor.statment.StatementHandler;
import com.esiazy.dynamic.sql.source.BoundSql;
import com.esiazy.dynamic.sql.source.Configuration;
import com.esiazy.dynamic.sql.source.SqlSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * sql执行器
 * 提供查询和更新服务
 *
 * @author wxf
 * @date 2021/4/15 20:23
 */
@Component
public class BaseExecutor implements Executor {
    private final Logger log = LoggerFactory.getLogger(getClass());

    protected Configuration configuration;

    public BaseExecutor(Configuration configuration) {
        this.configuration = configuration;
    }

    public static void setParam(PreparedStatement statement, BoundSql boundSql) throws SQLException {
        List<String> bindsParamName = boundSql.getBindsParamName();
        MetaHashMap map = boundSql.getParam();
        for (int i = 0; i < boundSql.getBindsParamName().size(); i++) {
            statement.setObject(i + 1, map.get(bindsParamName.get(i)));
        }
    }

    @Override
    public int update(DataSource dataSource, ExecuteContext contextMap, MetaHashMap parameter) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DataSourceUtils.getConnection(dataSource);
            BoundSql boundSql = getBoundSql(contextMap, parameter);
            StatementHandler handler = configuration.newStatementHandler(boundSql);
            statement = handler.prepare(connection);
            setParam((PreparedStatement) statement, boundSql);
            return handler.update(statement);
        } finally {
            closeStatement(statement);
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    private BoundSql getBoundSql(ExecuteContext contextMap, MetaHashMap parameter) {
        SqlSource source = contextMap.getSqlSource();
        return source.getBoundSql(parameter);
    }

    @Override
    public List<MetaHashMap> query(ExecuteContext contextMap, MetaHashMap parameter) throws SQLException {
        return this.query(contextMap.getDataSource(), contextMap, parameter);
    }

    @Override
    public List<MetaHashMap> query(DataSource dataSource, ExecuteContext context, MetaHashMap parameter) throws SQLException {
        Statement statement = null;
        Connection connection = null;
        try {
            connection = DataSourceUtils.getConnection(dataSource);
            BoundSql boundSql = getBoundSql(context, parameter);
            StatementHandler handler = configuration.newStatementHandler(boundSql);
            statement = handler.prepare(connection);
            setParam((PreparedStatement) statement, boundSql);
            return handler.query(statement);
        } finally {
            closeStatement(statement);
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    protected void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }
}
