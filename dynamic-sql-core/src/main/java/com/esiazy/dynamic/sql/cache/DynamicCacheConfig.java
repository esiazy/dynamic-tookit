package com.esiazy.dynamic.sql.cache;

import com.esiazy.dynamic.sql.exceptions.BuilderException;
import com.esiazy.dynamic.sql.executor.ExecuteContext;
import com.esiazy.dynamic.sql.script.xml.XmlLanguageDrive;
import com.esiazy.dynamic.sql.source.Configuration;
import com.esiazy.dynamic.sql.source.SqlSource;
import org.dom4j.DocumentException;

import javax.sql.DataSource;
import java.util.Date;

/**
 * @author wxf
 * @date 2021/5/29 8:36
 */
public class DynamicCacheConfig extends AbstractContextCacheConfig {
    private final Query query;

    private final XmlLanguageDrive drive;

    public DynamicCacheConfig(Configuration configuration, Query query) {
        super(configuration);
        this.query = query;
        this.drive = new XmlLanguageDrive();
    }

    @Override
    public ExecuteContext getOne(String contextId) {
        QueryEntity entity = query.selectOne(contextId);
        SqlSource sqlSource;
        try {
            sqlSource = drive.createSqlSource(entity.getSql());
        } catch (DocumentException e) {
            throw new BuilderException("build sql node exception: e", e);
        }
        ExecuteContext context = new ExecuteContext();
        context.setSqlSource(sqlSource);
        context.setDataSource(entity.getDataSource());
        context.setLastUpdateTime(entity.getUpdateTime());
        return context;
    }

    @Override
    public long getUpdateTime(String contextId) {
        return 0;
    }

    public interface Query {
        /**
         * 查询entity
         *
         * @param contextId contextID
         * @return 实体类结果
         */
        QueryEntity selectOne(String contextId);
    }

    public static class QueryEntity {
        private String sql;
        private Date updateTime;
        private DataSource dataSource;

        public String getSql() {
            return sql;
        }

        public void setSql(String sql) {
            this.sql = sql;
        }

        public Date getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Date updateTime) {
            this.updateTime = updateTime;
        }

        public DataSource getDataSource() {
            return dataSource;
        }

        public void setDataSource(DataSource dataSource) {
            this.dataSource = dataSource;
        }
    }
}
