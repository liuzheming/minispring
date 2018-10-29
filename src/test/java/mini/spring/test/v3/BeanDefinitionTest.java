package mini.spring.test.v3;

import mini.spring.beans.BeanDefinition;
import mini.spring.beans.ConstructorArgument;
import mini.spring.beans.factory.config.RuntimeBeanReference;
import mini.spring.beans.factory.config.TypedStringValue;
import mini.spring.beans.factory.support.DefaultBeanFactory;
import mini.spring.beans.factory.xml.XMLBeanDefinitionReader;
import mini.spring.beans.factory.xml.support.DefaultXMLBeanDefinitionReader;
import mini.spring.core.io.support.ClassPathResource;
import org.junit.Assert;
import org.junit.Test;

/**
 * Description: 1. 测试读取XML内定义的Constructor信息的功能
 * <p>
 * Created by lzm on  2018-10-29 .
 */
public class BeanDefinitionTest {

    @Test
    public void testReadXmlToBeanDefinition() {

        DefaultBeanFactory beanFactory = new DefaultBeanFactory();
        XMLBeanDefinitionReader reader = new DefaultXMLBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinition(new ClassPathResource("spring-config-v3.xml"));
        BeanDefinition bd = beanFactory.getBeanDefinition("petStore");
        ConstructorArgument consArg = bd.getConstructorArgument();

        Assert.assertEquals(4, consArg.size());

        ConstructorArgument.ValueHolder val0 = consArg.getArgumentValues().get(0);
        Assert.assertTrue(val0.getValue() instanceof RuntimeBeanReference);
        Assert.assertEquals("accountDao", ((RuntimeBeanReference) val0.getValue()).getBeanName());

        ConstructorArgument.ValueHolder val1 = consArg.getArgumentValues().get(1);
        Assert.assertTrue(val1.getValue() instanceof RuntimeBeanReference);
        Assert.assertEquals("itemDao", ((RuntimeBeanReference) val1.getValue()).getBeanName());

        ConstructorArgument.ValueHolder val2 = consArg.getArgumentValues().get(2);
        Assert.assertTrue(val2.getValue() instanceof TypedStringValue);
        Assert.assertEquals("lzm", ((TypedStringValue) val2.getValue()).getValue());

        ConstructorArgument.ValueHolder val3 = consArg.getArgumentValues().get(3);
        Assert.assertTrue(val3.getValue() instanceof TypedStringValue);
        Assert.assertEquals("1", ((TypedStringValue) val3.getValue()).getValue());

    }


}
