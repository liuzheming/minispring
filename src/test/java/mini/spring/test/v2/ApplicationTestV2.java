package mini.spring.test.v2;

import mini.spring.context.ApplicationContext;
import mini.spring.context.support.ClassPathXMLApplicationContext;
import mini.spring.test.entity.AccountDao;
import mini.spring.test.entity.ItemDao;
import mini.spring.test.entity.PetStore;
import org.junit.Assert;
import org.junit.Test;


/**
 * Description:
 * <p>
 * Created by lzm on  2018-10-26 .
 */
public class ApplicationTestV2 {

    @Test
    public void testGetBeanProperty() {

        ApplicationContext cxt = new ClassPathXMLApplicationContext("spring-config-v2.xml");
        PetStore petStore = (PetStore) cxt.getBean("petStore");

        Assert.assertNotNull(petStore.getAccountDao());
        Assert.assertNotNull(petStore.getItemDao());

        Assert.assertTrue(petStore.getAccountDao() instanceof AccountDao);
        Assert.assertTrue(petStore.getItemDao() instanceof ItemDao);

//        Assert.assertEquals("lzm",petStore.getOn);


    }


}
