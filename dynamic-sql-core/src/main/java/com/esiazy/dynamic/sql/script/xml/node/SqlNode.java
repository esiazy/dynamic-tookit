package com.esiazy.dynamic.sql.script.xml.node;

import com.esiazy.dynamic.sql.source.sqlsource.DynamicSqlContext;

import java.io.Serializable;

/**
 * @author wxf
 */
public interface SqlNode extends Serializable {

    boolean build(DynamicSqlContext context);

}