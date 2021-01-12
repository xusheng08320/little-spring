package org.litespring.beans.factory.config;

public class RuntimeBeanReference {
    private final String beanId;

    public RuntimeBeanReference(String beanId) {
        this.beanId = beanId;
    }

    public String getBeanId() {
        return beanId;
    }
}
