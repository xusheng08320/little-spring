package org.litespring.beans.factory.support;

import org.litespring.beans.factory.config.SingletonBeanRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(64);

    @Override
    public void registerSingleton(String beanId, Object singletonObject) {
        Object oldObject = this.singletonObjects.get(beanId);
        if (oldObject != null) {
            throw new IllegalStateException("Could not registry object[" + singletonObject + "] under bean id " + beanId + " there is already object [" + oldObject + "]");
        }
        this.singletonObjects.put(beanId, singletonObject);
    }

    @Override
    public Object getSingleton(String beanId) {
        return this.singletonObjects.get(beanId);
    }
}
