package com.esiazy.dynamic.sql.executor.result;

import com.esiazy.dynamic.core.entity.meta.MetaHashMap;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wxf
 * @date 2021/5/28 15:37
 */
public class MetaMapResultHandler implements ResultHandler {

    public final SimpleDateFormat HS_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public List<MetaHashMap> handlerResultSet(Statement stmt) throws SQLException {
        ResultSet resultSet = null;
        try {
            resultSet = stmt.getResultSet();
            List<MetaHashMap> metaMapList = new ArrayList<>(64);
            while (resultSet.next()) {
                MetaHashMap metaMap = new MetaHashMap(16);
                ResultSetMetaData resumed = resultSet.getMetaData();
                int cols = resumed.getColumnCount();
                for (int i = 1; i <= cols; ++i) {
                    String columnType = resumed.getColumnTypeName(i).toUpperCase();
//                    if ("DATETIME".equals(columnType) || "TIMESTAMP".equals(columnType) || "DATE".equals(columnType)) {
//                        Timestamp timestamp = resultSet.getTimestamp(i);
//                        if (timestamp == null) {
//                            metaMap.put(resumed.getColumnLabel(i), null);
//                            continue;
//                        }
////                        String dateTime = HS_FORMAT.format(timestamp);
//                        metaMap.put(resumed.getColumnLabel(i), resultSet.getObject(i));
//                    } else {
                    metaMap.put(resumed.getColumnLabel(i), resultSet.getObject(i));
//                    }
                }
                metaMapList.add(metaMap);
            }
            return metaMapList;
        } finally {
            closeResultSet(resultSet);
        }
    }

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
