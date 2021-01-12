package org.litespring.context.support;

import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypedStringValue;
import org.litespring.beans.factory.support.DefaultBeanFactory;

public class BeanDefinitionValueResolver {
    private final DefaultBeanFactory factory;

    public BeanDefinitionValueResolver(DefaultBeanFactory factory) {
        this.factory = factory;
    }

    public Object resolveValueIfNecessary(Object value) {
        if (value instanceof RuntimeBeanReference) {
            RuntimeBeanReference ref = (RuntimeBeanReference) value;
            Object bean = this.factory.getBean(ref.getBeanName());
            return bean;
        }
        if (value instanceof TypedStringValue) {
            TypedStringValue val = (TypedStringValue)value;
            return val.getValue();
        }
        throw new RuntimeException("the value " + value + " has not implementd");
    }
}
