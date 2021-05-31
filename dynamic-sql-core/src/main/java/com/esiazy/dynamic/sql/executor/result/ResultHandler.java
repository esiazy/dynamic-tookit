package com.esiazy.dynamic.sql.executor.result;

import com.esiazy.dynamic.core.entity.meta.MetaHashMap;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author wxf
 * @date 2021/5/28 15:40
 */
public interface ResultHandler {
    List<MetaHashMap> handlerResultSet(Statement stmt) throws SQLException;
}
