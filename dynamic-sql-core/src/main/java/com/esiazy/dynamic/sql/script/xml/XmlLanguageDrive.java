package com.esiazy.dynamic.sql.script.xml;

import com.esiazy.dynamic.sql.script.LanguageDrive;
import com.esiazy.dynamic.sql.source.SqlSource;
import com.esiazy.dynamic.sql.util.builder.XmlScriptBuilder;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

/**
 * xml驱动
 *
 * @author wxf
 * @date 2021/5/24 14:01
 */
public class XmlLanguageDrive implements LanguageDrive {

    @Override
    public SqlSource createSqlSource(String sql) throws DocumentException {
        Element element = getElement(sql);
        return createSqlSource(element);
    }

    @Override
    public SqlSource createSqlSource(Element element) {
        XmlScriptBuilder scriptBuilder = new XmlScriptBuilder(element);
        return scriptBuilder.parseScriptNode();
    }

    private Element getElement(String sql) throws DocumentException {
        SAXReader saxReader = new SAXReader();
        saxReader.setEncoding("UTF-8");
        Document document;
        sql = "<sql>" + sql + "</sql>";
        document = saxReader.read(new ByteArrayInputStream(sql.getBytes(StandardCharsets.UTF_8)));
        return document.getRootElement();
    }
}
