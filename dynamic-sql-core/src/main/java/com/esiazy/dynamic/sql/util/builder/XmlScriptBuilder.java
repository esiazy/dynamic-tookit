package com.esiazy.dynamic.sql.util.builder;

import com.esiazy.dynamic.sql.exceptions.NodeHandlerNotSupportException;
import com.esiazy.dynamic.sql.script.xml.node.*;
import com.esiazy.dynamic.sql.source.SqlSource;
import com.esiazy.dynamic.sql.source.sqlsource.DynamicSqlSource;
import com.esiazy.dynamic.sql.source.sqlsource.RawSqlSource;
import org.dom4j.Element;
import org.dom4j.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * xml解析生成器
 *
 * @author wxf
 * @date 2021/5/25 10:09
 */
public class XmlScriptBuilder {
    private final Element context;
    private final Map<String, NodeHandler> NODE_HANDLER_HASH_MAP = new HashMap<>(16);
    private boolean isDynamic;

    {
        NODE_HANDLER_HASH_MAP.put("if", new IfHandler());
        NODE_HANDLER_HASH_MAP.put("where", new WhereHandler());
    }

    public XmlScriptBuilder(Element context) {
        this.context = context;
    }

    /**
     * 解析根节点
     *
     * @return 动态sql源
     */
    public SqlSource parseScriptNode() {
        MultiSqlNode multiSqlNode = buildSqlNode(context);
        if (isDynamic) {
            return new DynamicSqlSource(multiSqlNode);
        } else {
            return new RawSqlSource(multiSqlNode);
        }
    }

    /**
     * 生成动态sql节点
     * <p>目前支持</p>
     * <p>where标签 {@link WhereHandler}</p>
     * <p>if标签 {@link IfHandler}</p>
     *
     * @param element 节点
     * @return 多节点sql对象
     * @see MultiSqlNode
     */
    @SuppressWarnings("unchecked")
    protected MultiSqlNode buildSqlNode(Element element) {
        List<SqlNode> contents = new ArrayList<>(16);
        List<Node> content = element.content();
        for (Node node : content) {
            if (Node.TEXT_NODE == node.getNodeType() || Node.CDATA_SECTION_NODE == node.getNodeType()) {
                String text = node.getText()
                        .replaceAll("[\t]|[\n]|[\n\t]|[\r]|[\r\n]", " ")
                        .replaceAll(" {2,}", " ");
                TextSqlNode textSqlNode = new TextSqlNode(text);
                if (textSqlNode.isDynamic()) {
                    contents.add(textSqlNode);
                } else {
                    contents.add(new StaticTextSqlNode(text));
                }
            } else if (Node.ELEMENT_NODE == node.getNodeType()) {
                String nodeType = node.getName();
                NodeHandler handler = NODE_HANDLER_HASH_MAP.get(nodeType);
                if (handler == null) {
                    throw new NodeHandlerNotSupportException("not found sqlNode handler.Maybe tags named[" + nodeType + "] not support");
                }
                handler.handler((Element) node, contents);
                isDynamic = true;
            }
        }
        return new MultiSqlNode(contents);
    }

    /**
     * 节点处理器
     */
    public interface NodeHandler {
        /**
         * handler
         *
         * @param handlerNode handler节点
         * @param targetList  目标集合
         */
        void handler(Element handlerNode, List<SqlNode> targetList);
    }

    /**
     * where节点处理器
     *
     * @see #buildSqlNode(Element)
     */
    private class WhereHandler implements NodeHandler {
        @Override
        public void handler(Element handlerNode, List<SqlNode> targetList) {
            MultiSqlNode multiSqlNode = buildSqlNode(handlerNode);
            WhereSqlNode ifSqlNode = new WhereSqlNode(multiSqlNode);
            targetList.add(ifSqlNode);
        }
    }

    /**
     * if节点处理器
     *
     * @see #buildSqlNode(Element)
     */
    private class IfHandler implements NodeHandler {
        @Override
        public void handler(Element handlerNode, List<SqlNode> targetList) {
            MultiSqlNode multiSqlNode = buildSqlNode(handlerNode);
            String test = handlerNode.attributeValue("test");
            IfSqlNode ifSqlNode = new IfSqlNode(test, multiSqlNode);
            targetList.add(ifSqlNode);
        }
    }
}
