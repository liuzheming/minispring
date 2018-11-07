package mini.spring.test.v4;

import mini.spring.beans.BeanDefinition;
import mini.spring.beans.factory.support.DefaultBeanFactory;
import mini.spring.beans.factory.support.GenericBeanDefinition;
import mini.spring.beans.factory.xml.XMLBeanDefinitionReader;
import mini.spring.beans.factory.xml.support.DefaultXMLBeanDefinitionReader;
import mini.spring.core.io.support.ClassPathResource;
import org.junit.Assert;
import org.junit.Test;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-11-01 .
 */
public class XmlBeanDefinitionReaderTest {

    @Test
    public void testGetBeanDefinition() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XMLBeanDefinitionReader bdReader = new DefaultXMLBeanDefinitionReader(factory);
        bdReader.loadBeanDefinition(new ClassPathResource("spring-config-v4.xml"));
        BeanDefinition bd = factory.getBeanDefinition("petStore");

        Assert.assertNotNull(bd);
        Assert.assertTrue(bd instanceof GenericBeanDefinition);

        Assert.assertNotNull(factory.getBean("petStore"));
    }


}
