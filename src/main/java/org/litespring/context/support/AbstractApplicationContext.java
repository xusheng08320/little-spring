package org.litespring.context.support;

import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.context.ApplicationContext;
import org.litespring.core.io.FileSystemResource;
import org.litespring.core.io.Resource;

public abstract class AbstractApplicationContext implements ApplicationContext {
    private String path;

    private DefaultBeanFactory defaultBeanFactory;

    public AbstractApplicationContext(String path) {
        defaultBeanFactory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(defaultBeanFactory);
        Resource resource = getResourceByPath(path);
        reader.loadBeanDefinitions(resource);
    }

    protected abstract Resource getResourceByPath(String path);

    @Override
    public Object getBean(String beanId) {
        return this.defaultBeanFactory.getBean(beanId);
    }
}
