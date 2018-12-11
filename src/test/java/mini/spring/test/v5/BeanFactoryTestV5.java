package mini.spring.test.v5;

import mini.spring.aop.Advice;
import mini.spring.beans.factory.BeanFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Description: 7、测试是否已将Advice对象注册到BeanFactory
 * <p>
 * Created by lzm on  2018-12-11.
 */
public class BeanFactoryTestV5 extends AbstractTestV5 {

    @Test
    public void testGetBeanByType() {
        BeanFactory bf = getBeanFactory("spring-config-v5.xml");
        List<Object> advices = bf.getBeansByType(Advice.class);


        Assert.assertEquals(3, advices.size());


    }

}
