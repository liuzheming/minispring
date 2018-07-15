package mini.spring.test.v1;


import static org.junit.Assert.*;

import mini.spring.beans.BeanDefinition;
import mini.spring.beans.factory.BeanFactory;
import mini.spring.beans.factory.support.DefaultBeanFactory;
import org.junit.Test;

/**
 * 测试用例 v0.1
 * 针对给定XML，能够获取相应的BeanDefinition和BeanInstance
 */
public class BeanFactoryTest {


    /**
     * 测试获取Bean定义
     * 测试获取Bean实例
     */
    @Test
    public void testGetBean() {

        BeanFactory bf = new DefaultBeanFactory("spring-config-v1.xml");
        BeanDefinition bd = bf.getBeanDefinition("petStore");

        assertEquals(bd.getBeanClassName(), "mini.spring.test.v1.PetStore");

        PetStore petStore = (PetStore) bf.getBean("petStore");

//        assertNotNull(petStore);

    }



}
