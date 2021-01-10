package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanFactory;

public class DefaultBeanFactory implements BeanFactory {

    public DefaultBeanFactory(String configFile) {

    }

    @Override
    public BeanDefinition getBeanDefinition(String beanId) {
        return null;
    }

    @Override
    public Object getBean(String beanId) {
        return null;
    }
}
