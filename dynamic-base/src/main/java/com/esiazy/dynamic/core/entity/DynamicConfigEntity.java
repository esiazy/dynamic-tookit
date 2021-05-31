package com.esiazy.dynamic.core.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wxf
 */
public class DynamicConfigEntity implements Serializable {
    private static final long serialVersionUID = 8240556940488367656L;

    /**
     * 控制器
     */
    private String controller;

    /**
     * 执行代码
     */
    private String code;

    /**
     * 最后更新时间
     */
    private Date updateTime;

    public static Builder builder() {
        return new Builder();
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public static final class Builder {
        private final DynamicConfigEntity dynamicConfigEntity;

        private Builder() {
            dynamicConfigEntity = new DynamicConfigEntity();
        }

        public Builder controller(String controller) {
            dynamicConfigEntity.setController(controller);
            return this;
        }

        public Builder code(String code) {
            dynamicConfigEntity.setCode(code);
            return this;
        }

        public Builder updateTime(Date updateTime) {
            dynamicConfigEntity.setUpdateTime(updateTime);
            return this;
        }

        public DynamicConfigEntity build() {
            return dynamicConfigEntity;
        }
    }
}