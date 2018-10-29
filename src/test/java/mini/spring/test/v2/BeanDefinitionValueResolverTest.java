package mini.spring.test.v2;

import mini.spring.beans.factory.config.RuntimeBeanReference;
import mini.spring.beans.factory.config.TypedStringValue;
import mini.spring.beans.factory.support.BeanDefinitionValueResolver;
import mini.spring.beans.factory.support.DefaultBeanFactory;
import mini.spring.beans.factory.xml.XMLBeanDefinitionReader;
import mini.spring.beans.factory.xml.support.DefaultXMLBeanDefinitionReader;
import mini.spring.core.io.support.ClassPathResource;
import mini.spring.test.entity.AccountDao;
import org.junit.Assert;
import org.junit.Test;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-10-26 .
 */
public class BeanDefinitionValueResolverTest {


    @Test
    public void testResolveBeanDefinitionReference() {
        DefaultBeanFactory bf = new DefaultBeanFactory();
        XMLBeanDefinitionReader bdReader = new DefaultXMLBeanDefinitionReader(bf);
        bdReader.loadBeanDefinition(new ClassPathResource("spring-config-v2.xml"));

        BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(bf);

        RuntimeBeanReference ref = new RuntimeBeanReference("accountDao");
        Object value = resolver.resolveValueIfNecessary(ref);
        Assert.assertNotNull(value);
        Assert.assertTrue(value instanceof AccountDao);

    }

    @Test
    public void testResolveBeanDefinitionValue() {
        DefaultBeanFactory bf = new DefaultBeanFactory();
        XMLBeanDefinitionReader reader = new DefaultXMLBeanDefinitionReader(bf);
        reader.loadBeanDefinition(new ClassPathResource("spring-config-v2.xml"));

        BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(bf);

        TypedStringValue strVal = new TypedStringValue("lzm");
        String value = (String) resolver.resolveValueIfNecessary(strVal);

        Assert.assertNotNull(value);
        Assert.assertEquals(value, "lzm");
    }

}
