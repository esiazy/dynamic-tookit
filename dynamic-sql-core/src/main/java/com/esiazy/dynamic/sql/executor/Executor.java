package com.esiazy.dynamic.sql.executor;

import com.esiazy.dynamic.sql.executor.result.ResultHandler;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author wxf
 */
public interface Executor {

    /**
     * 执行dml
     *
     * @param context   sql所需要的上下文
     * @param parameter parameter
     * @return 影响条数
     * @throws SQLException SQLException
     */
    default int update(ExecuteContext context, Map<String, Object> parameter) throws SQLException {
        return this.update(context.getDataSource(), context, parameter);
    }

    int update(DataSource dataSource, ExecuteContext contextMap, Map<String, Object> parameter) throws SQLException;

    <E> List<E> query(DataSource dataSource, ExecuteContext context, Map<String, Object> parameter, ResultHandler resultHandler) throws SQLException;
}
