package com.esiazy.dynamic.sql.executor;

import com.esiazy.dynamic.sql.source.SqlSource;

import javax.sql.DataSource;
import java.util.Date;

/**
 * sql执行上下文
 *
 * @author wxf
 * @date 2021/4/16 14:03
 */
public class ExecuteContext {
    /**
     * sql源
     */
    private SqlSource sqlSource;

    /**
     * 上次更新时间
     */
    private Date lastUpdateTime;

    /**
     * datasource
     */
    private DataSource dataSource;

    public SqlSource getSqlSource() {
        return sqlSource;
    }

    public void setSqlSource(SqlSource sqlSource) {
        this.sqlSource = sqlSource;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
