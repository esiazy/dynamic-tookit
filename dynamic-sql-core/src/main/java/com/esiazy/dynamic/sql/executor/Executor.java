package com.esiazy.dynamic.sql.executor;


import com.esiazy.dynamic.core.entity.meta.MetaHashMap;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

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
    default int update(ExecuteContext context, MetaHashMap parameter) throws SQLException {
        return this.update(context.getDataSource(), context, parameter);
    }

    int update(DataSource dataSource, ExecuteContext contextMap, MetaHashMap parameter) throws SQLException;


    /**
     * 执行查询sql
     *
     * @param context   sql所需要的上下文
     * @param parameter parameter
     * @return 返回handler处理后的数据
     * @throws SQLException SQLException
     */
    default List<MetaHashMap> query(ExecuteContext context, MetaHashMap parameter) throws SQLException {
        return this.query(context.getDataSource(), context, parameter);
    }

    List<MetaHashMap> query(DataSource dataSource, ExecuteContext context, MetaHashMap parameter) throws SQLException;
}
