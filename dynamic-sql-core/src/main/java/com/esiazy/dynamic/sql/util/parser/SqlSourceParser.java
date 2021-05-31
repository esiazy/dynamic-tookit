package com.esiazy.dynamic.sql.util.parser;

import com.esiazy.dynamic.core.entity.meta.MetaHashMap;
import com.esiazy.dynamic.sql.source.SqlSource;
import com.esiazy.dynamic.sql.source.sqlsource.StaticSqlSource;

import java.util.ArrayList;
import java.util.List;

/**
 * sql符号解析
 *
 * @author wxf
 * @date 2021/5/28 10:43
 */
public final class SqlSourceParser {

    public SqlSource parseSharp(String originalSql, MetaHashMap params) {
        ParameterMappingTokenHandler handler = new ParameterMappingTokenHandler(params);
        GenericTokenParser parser = new GenericTokenParser("#{", "}", handler);
        String sql = parser.parse(originalSql);
        return new StaticSqlSource(sql, handler.getBindParamNames());
    }


    private static class ParameterMappingTokenHandler implements TokenHandler {
        private final List<String> bindParamNames = new ArrayList<>();

        private final MetaHashMap parameters;

        public ParameterMappingTokenHandler(MetaHashMap parameters) {
            this.parameters = parameters;
        }

        public List<String> getBindParamNames() {
            return bindParamNames;
        }

        @Override
        public String handleToken(String content) {
            bindParamNames.add(buildParameterMapping(content));
            return "?";
        }

        private String buildParameterMapping(String content) {
            parameters.get(content);
            return content;
        }
    }

}
