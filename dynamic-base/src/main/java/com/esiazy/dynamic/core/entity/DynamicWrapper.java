package com.esiazy.dynamic.core.entity;

import java.io.Serializable;
import java.util.Map;

/**
 * @author wxf
 */
public class DynamicWrapper implements Serializable {
    private static final long serialVersionUID = -6169328101999890723L;
    private final Dto dto;

    public DynamicWrapper() {
        dto = new Dto();
    }

    public DynamicWrapper controller(String controller) {
        dto.setController(controller);
        return this;
    }

    public DynamicWrapper method(String method) {
        dto.setMethod(method);
        return this;
    }

    public DynamicWrapper param(Map<String, Object> param) {
        dto.setParam(param);
        return this;
    }

    public String getController() {
        return dto.getController();
    }

    public String getMethod() {
        return dto.getMethod();
    }

    public Map<String, Object> getParam() {
        return dto.getParam();
    }

    public boolean isPrivate() {
        return dto.isPrivate;
    }

    public DynamicWrapper privates(boolean isPrivate) {
        dto.setPrivate(isPrivate);
        return this;
    }

    public Object getParameter() {
        return dto.getParameter();
    }

    public DynamicWrapper parameter(Object parameter) {
        dto.setParameter(parameter);
        return this;
    }

    private static class Dto {
        private String controller;

        private String method;

        private Map<String, Object> param;

        private boolean isPrivate;

        private Object parameter;

        public String getController() {
            return controller;
        }

        public void setController(String controller) {
            this.controller = controller;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public Map<String, Object> getParam() {
            return param;
        }

        public void setParam(Map<String, Object> param) {
            this.param = param;
        }

        public boolean isPrivate() {
            return isPrivate;
        }

        public void setPrivate(boolean aPrivate) {
            isPrivate = aPrivate;
        }

        public Object getParameter() {
            return parameter;
        }

        public void setParameter(Object parameter) {
            this.parameter = parameter;
        }
    }
}