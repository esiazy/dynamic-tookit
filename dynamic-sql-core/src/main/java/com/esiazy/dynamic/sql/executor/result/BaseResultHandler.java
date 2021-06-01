package com.esiazy.dynamic.sql.executor.result;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author wxf
 * @date 2021/6/1 4:37 下午
 */
public abstract class BaseResultHandler implements ResultHandler {

    @Override
    @SuppressWarnings("unchecked")
    public <E> List<E> handlerResultSet(Statement stmt) throws SQLException {
        ResultSet resultSet = null;
        try {
            resultSet = stmt.getResultSet();
            return (List<E>) handler(resultSet);
        } finally {
            closeResultSet(resultSet);
        }
    }

    protected abstract List<Object> handler(ResultSet resultSet) throws SQLException;

    protected void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                if (!resultSet.isClosed()) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                // ignore
            }
        }
    }
}
