package mini.spring.test.v5;

import mini.spring.aop.config.MethodLocatingFactory;
import mini.spring.beans.factory.support.DefaultBeanFactory;
import mini.spring.beans.factory.xml.XMLBeanDefinitionReader;
import mini.spring.beans.factory.xml.support.DefaultXMLBeanDefinitionReader;
import mini.spring.core.io.Resource;
import mini.spring.core.io.support.ClassPathResource;
import mini.spring.test.tx.TransactionMgr;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * Description: 2、测试从BeanFactory中，获取被注册的Aspect方法
 * <p>
 * Created by lzm on  2018-11-15.
 */
public class MethodLocatingFactoryTest {


    @Test
    public void testGetMethod() throws NoSuchMethodException {

        DefaultBeanFactory beanFactory = new DefaultBeanFactory();
        XMLBeanDefinitionReader reader = new DefaultXMLBeanDefinitionReader(beanFactory);
        Resource resource = new ClassPathResource("spring-config-v5.xml");
        reader.loadBeanDefinition(resource);

        {
            MethodLocatingFactory mlf = new MethodLocatingFactory();
            mlf.setTargetBeanName("tx");
            mlf.setMethodName("start");
            mlf.setBeanFactory(beanFactory);

            Method m = mlf.getObject();

            Assert.assertEquals(TransactionMgr.class, m.getDeclaringClass());
            Assert.assertEquals(TransactionMgr.class.getMethod("start"), m);
        }
        {
            MethodLocatingFactory mlf = new MethodLocatingFactory();
            mlf.setTargetBeanName("tx");
            mlf.setMethodName("commit");
            mlf.setBeanFactory(beanFactory);

            Method m = mlf.getObject();

            Assert.assertEquals(TransactionMgr.class, m.getDeclaringClass());
            Assert.assertEquals(TransactionMgr.class.getMethod("commit"), m);
        }
        {
            MethodLocatingFactory mlf = new MethodLocatingFactory();
            mlf.setTargetBeanName("tx");
            mlf.setMethodName("rollback");
            mlf.setBeanFactory(beanFactory);

            Method m = mlf.getObject();

            Assert.assertEquals(TransactionMgr.class, m.getDeclaringClass());
            Assert.assertEquals(TransactionMgr.class.getMethod("rollback"), m);
        }
    }


}
