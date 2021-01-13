package org.litespring.beans.factory;

import org.litespring.beans.factory.config.DependencyDescriptor;

public interface AutowiredCapableBeanFactory extends BeanFactory{
    Object resolveDependency(DependencyDescriptor descriptor);
}
