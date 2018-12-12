package mini.spring.test.v5;

import mini.spring.aop.config.MethodLocatingFactory;
import mini.spring.beans.factory.support.DefaultBeanFactory;
import mini.spring.beans.factory.xml.XMLBeanDefinitionReader;
import mini.spring.beans.factory.xml.support.DefaultXMLBeanDefinitionReader;
import mini.spring.context.ApplicationContext;
import mini.spring.context.support.ClassPathXMLApplicationContext;
import mini.spring.core.io.support.ClassPathResource;
import mini.spring.test.entity.PetStore;
import mini.spring.test.v4.XmlBeanDefinitionReaderTest;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * Description:
 * <p>  动态代理分有两种形式，  1、在编译器处理,如AspectJ
 * <p>                      2、在运行时处理,如Spring,需要注意的是,虽然spring依赖于AspectJ的部分代码,
 * <p>                         但是spring是在运行时进行加强的
 * Created by lzm on  2018-11-14.
 */
public class ApplicationCtxTestV5 {


    @Test
    public void testGetMethod() throws Exception {

        ApplicationContext ctx = new ClassPathXMLApplicationContext("spring-config-v5.xml");
        PetStore ps = (PetStore) ctx.getBean("petStore");
        ps.placeOrder(110);
    }


}
