package org.litespring.test.v2;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.context.ApplicationContext;
import org.litespring.context.support.ClassPathXmlApplicationContext;
import org.litespring.dao.v2.AccountDao;
import org.litespring.dao.v2.ItemDao;
import org.litespring.service.v2.PetStoreService;

public class ApplicationContextTestV2 {

    @Test
    public void testGetBean() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v2.xml");
        PetStoreService petStoreService = (PetStoreService) ctx.getBean("petStore");
        Assert.assertNotNull(petStoreService.getAccountDao());
        Assert.assertNotNull(petStoreService.getItemDao());
        Assert.assertTrue(petStoreService.getAccountDao() instanceof AccountDao);
        Assert.assertTrue(petStoreService.getItemDao() instanceof ItemDao);
        Assert.assertEquals("xusheng", petStoreService.getOwner());
        Assert.assertEquals(1, petStoreService.getVersion());
    }
}
