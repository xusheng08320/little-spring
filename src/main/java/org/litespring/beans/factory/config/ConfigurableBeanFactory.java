package org.litespring.beans.factory.config;

import org.litespring.beans.factory.AutowiredCapableBeanFactory;

import java.util.List;

public interface ConfigurableBeanFactory extends AutowiredCapableBeanFactory {
    void setBeanClassLoader(ClassLoader classLoader);
    ClassLoader getBeanClassLoader();
    void addBeanPostProcessor(BeanPostProcessor postProcessor);
    List<BeanPostProcessor> getBeanPostProcessors();
}
