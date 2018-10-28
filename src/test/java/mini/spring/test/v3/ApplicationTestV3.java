package mini.spring.test.v3;

import mini.spring.context.ApplicationContext;
import mini.spring.context.support.ClassPathXMLApplicationContext;
import mini.spring.test.entity.PetStore;
import org.junit.Assert;
import org.junit.Test;

/**
 * Description:
 * <p>
 * Created by lzm on 2018/10/28.
 */
public class ApplicationTestV3 {

    @Test
    public void testConstructorInjection() {

        ApplicationContext ctx = new ClassPathXMLApplicationContext("spring-config-v3.xml");
        PetStore ps = (PetStore) ctx.getBean("petStore");

        Assert.assertNotNull(ps.getAccountDao());
        Assert.assertNotNull(ps.getItemDao());
        Assert.assertEquals(1, ps.getVersion());

    }

}
