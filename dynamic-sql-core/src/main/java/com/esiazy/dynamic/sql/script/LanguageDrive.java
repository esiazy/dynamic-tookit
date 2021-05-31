package com.esiazy.dynamic.sql.script;

import com.esiazy.dynamic.sql.source.SqlSource;
import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * sql语言驱动
 *
 * @author wxf
 * @date 2021/5/28 11:27
 */
public interface LanguageDrive {
    /**
     * 根据文本创建sql源信息
     *
     * @param text 文本信息
     * @return 数据源对象
     * @throws DocumentException dom文档解析错误
     */
    SqlSource createSqlSource(String text) throws DocumentException;


    /**
     * 根据dom创建sql源信息
     *
     * @param element dom节点
     * @return 数据源对象
     */
    SqlSource createSqlSource(Element element);
}
