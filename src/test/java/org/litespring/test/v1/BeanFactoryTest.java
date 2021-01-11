package org.litespring.test.v1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XMLBeanDefinitionReader;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.Resource;
import org.litespring.service.v1.PetStoreService;

public class BeanFactoryTest {
    private DefaultBeanFactory factory = null;
    private XMLBeanDefinitionReader reader = null;

    @Before
    public void setUp() {
        factory = new DefaultBeanFactory();
        reader = new XMLBeanDefinitionReader(factory);
    }

    @Test
    public void testGetBean() {
        Resource resource = new ClassPathResource("petstore-v1.xml");
        reader.loadBeanDefinitions(resource);
        BeanDefinition beanDefinition = factory.getBeanDefinition("petStore");
        Assert.assertTrue(beanDefinition.isSingleton());
        Assert.assertFalse(beanDefinition.isPrototype());
        Assert.assertEquals(BeanDefinition.SCOPE_DEFAULT, beanDefinition.getScope());

        Assert.assertEquals("org.litespring.service.v1.PetStoreService", beanDefinition.getBeanClassName());
        PetStoreService petStoreService = (PetStoreService) factory.getBean("petStore");
        Assert.assertNotNull(petStoreService);
    }

    @Test
    public void testGetBeanScope() {
        Resource resource = new ClassPathResource("petstore-v1.xml");
        reader.loadBeanDefinitions(resource);
        BeanDefinition beanDefinition = factory.getBeanDefinition("petStore1");
        Assert.assertFalse(beanDefinition.isSingleton());
        Assert.assertTrue(beanDefinition.isPrototype());
        Assert.assertEquals(BeanDefinition.SCOPE_PROTOTYPE, beanDefinition.getScope());
    }

    @Test
    public void testInvalidBean() {
        try {
            factory.getBean("invalidBean");
        } catch (BeanCreationException e){
            return;
        }
        Assert.fail("test invalid bean failed");
    }

    @Test
    public void testInvalidXML() {
        try {
            Resource resource = new ClassPathResource("xxx.xml");
            reader.loadBeanDefinitions(resource);
        } catch (BeanDefinitionStoreException e) {
            return;
        }
        Assert.fail("expect BeanDefinitionStoreException");
    }

}
