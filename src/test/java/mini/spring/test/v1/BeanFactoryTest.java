package mini.spring.test.v1;


import mini.spring.beans.BeanDefinition;
import mini.spring.beans.factory.BeanFactory;
import mini.spring.beans.factory.support.BeanDefinitionRegistry;
import mini.spring.beans.factory.support.DefaultBeanFactory;
import mini.spring.beans.factory.xml.XMLBeanDefinitionReader;
import mini.spring.beans.factory.xml.support.DefaultXMLBeanDefinitionReader;
import mini.spring.core.io.support.ClassPathResource;
import mini.spring.test.entity.PetStore;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * 测试用例 v0.1
 * 针对给定XML，能够获取相应的BeanDefinition和BeanInstance
 */
public class BeanFactoryTest {


    private BeanFactory bf = null;

    private XMLBeanDefinitionReader xmlReader = null;

    @Before
    public void setUP() {
        bf = new DefaultBeanFactory();
        xmlReader = new DefaultXMLBeanDefinitionReader(bf);
    }

    /**
     * 测试获取Bean定义
     * 测试获取Bean实例
     */
    @Test
    public void testGetBean() {
        /*  BeanFactory兼职搞xml加载的版本 */
//        BeanFactory bf = new DefaultBeanFactory("spring-config-v1.xml");
//        BeanDefinition bd = bf.getBeanDefinition("petStore");
//        assertEquals(bd.getBeanClassName(), "mini.spring.test.v1.PetStore");
//        PetStore petStore = (PetStore) bf.getBean("petStore");
//        assertNotNull(petStore);

        /* 加载xml的功能从BeanFactory拆分出来的版本 */

        // 读取XML和注册BeanDefinition
        xmlReader.loadBeanDefinition(new ClassPathResource("spring-config-v1.xml"));
        BeanDefinition petStoreDef = ((BeanDefinitionRegistry) bf).getBeanDefinition("petStore");
        Assert.assertEquals("mini.spring.test.entity.PetStore", petStoreDef.getBeanClassName());
        PetStore petStore = (PetStore) bf.getBean("petStore");
        Assert.assertNotNull(petStore);
    }

    @Test
    public void testGetSingleton() {
        BeanFactory bf = new DefaultBeanFactory();
        XMLBeanDefinitionReader xmlReader = new DefaultXMLBeanDefinitionReader(bf);
        xmlReader.loadBeanDefinition(new ClassPathResource("spring-config-v1.xml"));
        PetStore petStore = (PetStore) bf.getBean("petStore");
        PetStore petStore2 = (PetStore) bf.getBean("petStore");
        Assert.assertEquals(petStore, petStore2);
    }
}
