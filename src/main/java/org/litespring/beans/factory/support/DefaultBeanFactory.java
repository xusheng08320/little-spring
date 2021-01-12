package org.litespring.beans.factory.support;

import com.sun.xml.internal.ws.api.message.stream.InputStreamMessage;
import org.apache.commons.beanutils.BeanUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.PropertyValue;
import org.litespring.beans.SimpleTypeConverter;
import org.litespring.beans.TypeConverter;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;
import org.litespring.context.support.BeanDefinitionValueResolver;
import org.litespring.util.ClassUtils;
import org.omg.CORBA.TRANSACTION_MODE;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class DefaultBeanFactory extends DefaultSingletonBeanRegistry
        implements BeanDefinitionRegistry, ConfigurableBeanFactory {

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(64);

    private ClassLoader beanClassLoader = null;

    public DefaultBeanFactory() {
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanId) {
        return this.beanDefinitionMap.get(beanId);
    }

    @Override
    public void registryBeanDefinition(String beanId, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(beanId, beanDefinition);
    }

    @Override
    public Object getBean(String beanId) {
        BeanDefinition beanDefinition = this.getBeanDefinition(beanId);
        if (beanDefinition == null) {
            throw new BeanCreationException("Bean Definition does not exist");
        }
        if (beanDefinition.isSingleton()) {
            Object bean = super.getSingleton(beanId);
            if (bean == null) {
                bean = createBean(beanDefinition);
                this.registerSingleton(beanId, bean);
            }
            return bean;
        }
        return createBean(beanDefinition);
    }

    private Object createBean(BeanDefinition beanDefinition) {
        Object bean = instantiateBean(beanDefinition);
        populateBean(beanDefinition, bean);
        return bean;
    }

    private void populateBeanUseCommonBeanUtils(BeanDefinition beanDefinition, Object bean) {
        List<PropertyValue> pvs = beanDefinition.getPropertyValues();
        if (pvs == null || pvs.isEmpty()) {
            return;
        }
        BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(this);
        try {
            for (PropertyValue pv : pvs) {
                String propertyName = pv.getName();
                Object originValue = pv.getValue();
                Object resolvedValue = resolver.resolveValueIfNecessary(originValue);
                BeanUtils.copyProperty(bean, propertyName, resolvedValue);
            }
        } catch (Exception e) {
            throw new BeanCreationException("Failed to obtain BeanInfo for class [" + beanDefinition.getBeanClassName() + "]", e);
        }
    }

    private void populateBean(BeanDefinition beanDefinition, Object bean) {
        List<PropertyValue> pvs = beanDefinition.getPropertyValues();
        if (pvs == null || pvs.isEmpty()) {
            return;
        }
        BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(this);
        try {
            for (PropertyValue pv : pvs) {
                String propertyName = pv.getName();
                Object originValue = pv.getValue();
                Object resolvedValue = resolver.resolveValueIfNecessary(originValue);
                BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
                PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
                TypeConverter typeConverter = new SimpleTypeConverter();
                for (PropertyDescriptor pd : pds) {
                    if (pd.getName().equals(propertyName)) {
                        Object valueConvertered = typeConverter.convertIfNecessary(resolvedValue, pd.getPropertyType());
                        pd.getWriteMethod().invoke(bean, valueConvertered);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new BeanCreationException("Failed to obtain BeanInfo for class [" + beanDefinition.getBeanClassName() + "]", e);
        }
    }

    private Object instantiateBean(BeanDefinition beanDefinition) {
        ClassLoader cl = this.getBeanClassLoader();
        String beanClassName = beanDefinition.getBeanClassName();
        try {
            Class<?> clz = cl.loadClass(beanClassName);
            return clz.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("Create bean for " + beanClassName + " failed", e);
        }
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
    }

    @Override
    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader();
    }
}
