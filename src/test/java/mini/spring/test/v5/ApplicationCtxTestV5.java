package mini.spring.test.v5;

import mini.spring.beans.factory.support.DefaultBeanFactory;
import mini.spring.beans.factory.xml.XMLBeanDefinitionReader;
import mini.spring.beans.factory.xml.support.DefaultXMLBeanDefinitionReader;
import mini.spring.core.io.support.ClassPathResource;
import mini.spring.test.v4.XmlBeanDefinitionReaderTest;
import org.junit.Assert;

import java.lang.reflect.Method;

/**
 * Description:
 * <p>  动态代理分有两种形式，  1、在编译器处理,如AspectJ
 * <p>                      2、在运行时处理,如Spring,需要注意的是,虽然spring依赖于AspectJ的部分代码,
 * <p>                         但是spring是在运行时进行加强的
 * Created by lzm on  2018-11-14.
 */
public class ApplicationCtxTestV5 {


    public void testGetMethod() throws Exception {

//        DefaultBeanFactory factory = new DefaultBeanFactory();
//        XMLBeanDefinitionReader reader = new DefaultXMLBeanDefinitionReader(factory);
//        reader.loadBeanDefinition(new ClassPathResource("spring-config-v5.xml"));
//
//        MethodLocationFactory methodLocatingFactory = new MethodLocatingFactory();
//        methodLocatingFactory.setTargetBeanName("tx");
//        methodLocatingFactory.setMethodName("start");
//        methodLocatingFactory.setBeanFactory(factory);
//
//        Method m = methodLocatingFactory.getObject();
//
//        Assert.assertTrue(TransactionManager.class.quials(m.getDeclaringClass()));
//        Assert.assertTrue(m.equals(TransactionManager.class.getMethod("start")));

    }


}
