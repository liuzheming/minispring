package mini.spring.test.v4;

import javafx.application.Application;
import mini.spring.context.ApplicationContext;
import mini.spring.context.support.ClassPathXMLApplicationContext;
import mini.spring.test.entity.PetStore;
import org.junit.Assert;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-11-09.
 */
public class ApplicationTestV4 {


    public void testV4() {

        ApplicationContext ctx = new ClassPathXMLApplicationContext("spring-config-v4.xml");
        PetStore petStore = (PetStore) ctx.getBean("petStore");

        Assert.assertNotNull(petStore);
        Assert.assertNotNull(petStore.getAccountDao());
        Assert.assertNotNull(petStore.getItemDao());

    }

}
