package org.litespring.beans.factory.config;

public interface SingletonBeanRegistry {
    void registerSingleton(String beanId, Object singletonObject);
    Object getSingleton(String beanId);
}
