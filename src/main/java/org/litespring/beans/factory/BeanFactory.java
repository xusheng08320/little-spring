package org.litespring.beans.factory;

import org.litespring.beans.BeanDefinition;

public interface BeanFactory {

    Object getBean(String beanId);

    Class<?> getType(String name) throws NoSuchBeanDefinitionException;
}
