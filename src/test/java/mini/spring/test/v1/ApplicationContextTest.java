package mini.spring.test.v1;

import mini.spring.context.ApplicationContext;
import mini.spring.context.support.ClassPathXMLApplicationContext;
import mini.spring.context.support.FileSystemXMLApplicationContext;
import mini.spring.test.entity.PetStore;
import org.junit.Assert;
import org.junit.Test;

/**
 * Description:
 * <p>
 * Created by lzm on 2018/7/19.
 */
public class ApplicationContextTest {


    @Test
    public void testCxtGetBean() {
        String path = "spring-config-v1.xml";
        ApplicationContext cxt = new ClassPathXMLApplicationContext(path);
        PetStore petStore = (PetStore) cxt.getBean("petStore");
        PetStore petStore1 = (PetStore) cxt.getBean("petStore");
        Assert.assertNotNull(petStore);
        Assert.assertEquals(petStore, petStore1);
    }

    @Test
    public void testFxtGetBean() {
        String path = "/Users/lzm/Documents/IEDA_Workspace/minispring/src/test/resources/spring-config-v1.xml";
        ApplicationContext fxt = new FileSystemXMLApplicationContext(path);
        PetStore petStore = (PetStore) fxt.getBean("petStore");
        PetStore petStore1 = (PetStore) fxt.getBean("petStore");
        Assert.assertNotNull(petStore);
        Assert.assertEquals(petStore, petStore1);
    }

}
