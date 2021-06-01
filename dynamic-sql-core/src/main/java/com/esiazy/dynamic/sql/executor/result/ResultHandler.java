package com.esiazy.dynamic.sql.executor.result;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author wxf
 * @date 2021/5/28 15:40
 */
public interface ResultHandler {

    /**
     * 处理结果
     *
     * @param stmt stmt
     * @param <E>  泛型
     * @return 结果集合
     * @throws SQLException sql异常信息
     */
    <E> List<E> handlerResultSet(Statement stmt) throws SQLException;
}
