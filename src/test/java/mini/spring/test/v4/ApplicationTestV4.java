package mini.spring.test.v4;

import mini.spring.context.ApplicationContext;
import mini.spring.context.support.ClassPathXMLApplicationContext;
import mini.spring.test.entity.PetStore;
import org.junit.Assert;
import org.junit.Test;

/**
 * Description: 9、测试完整注入功能
 * <p>
 * Created by lzm on  2018-11-13.
 */
public class ApplicationTestV4 {


    @Test
    public void test() {

        ApplicationContext ctx = new ClassPathXMLApplicationContext("spring-config-v4.xml");

        PetStore petStore = (PetStore) ctx.getBean("petStore");

        Assert.assertNotNull(petStore);
        Assert.assertNotNull(petStore.getAccountDao());
        Assert.assertNotNull(petStore.getItemDao());

    }

}
