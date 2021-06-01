package com.esiazy.dynamic.sql.executor.result;

import com.esiazy.dynamic.core.entity.meta.MetaHashMap;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wxf
 * @date 2021/5/28 15:37
 */
public class MetaMapResultHandler extends BaseResultHandler {
    private final DateTimeFormatter dateTimeFormatter;

    public MetaMapResultHandler(String format) {
        this.dateTimeFormatter = DateTimeFormatter.ofPattern(format);
    }

    @Override
    protected List<Object> handler(ResultSet resultSet) throws SQLException {
        List<Object> objects = new ArrayList<>(128);
        while (resultSet.next()) {
            MetaHashMap metaMap = new MetaHashMap(16);
            ResultSetMetaData resumed = resultSet.getMetaData();
            int cols = resumed.getColumnCount();
            for (int i = 1; i <= cols; ++i) {
                String columnType = resumed.getColumnTypeName(i).toUpperCase();
                //对时间进行处理
                if ("DATETIME".equals(columnType) || "TIMESTAMP".equals(columnType) || "DATE".equals(columnType)) {
                    Timestamp timestamp = resultSet.getTimestamp(i);
                    if (timestamp == null) {
                        metaMap.put(resumed.getColumnLabel(i), null);
                        continue;
                    }
                    String dateTime = timestamp.toLocalDateTime().format(dateTimeFormatter);
                    metaMap.put(resumed.getColumnLabel(i), dateTime);
                } else {
                    metaMap.put(resumed.getColumnLabel(i), resultSet.getObject(i));
                }
            }
            objects.add(metaMap);
        }
        return objects;
    }
}
