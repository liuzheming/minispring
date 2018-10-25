package mini.spring.test.v2;

import mini.spring.beans.BeanDefinition;
import mini.spring.beans.PropertyValue;
import mini.spring.beans.factory.config.RuntimeBeanReference;
import mini.spring.beans.factory.support.DefaultBeanFactory;
import mini.spring.beans.factory.xml.XMLBeanDefinitionReader;
import mini.spring.beans.factory.xml.support.DefaultXMLBeanDefinitionReader;
import mini.spring.core.io.support.ClassPathResource;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Description:
 * <p>
 * Created by lzm on 2018/10/23.
 */
public class SetterInjectTest {

    @Test
    public void testSetter() {

//        ApplicationContext cxt =
//                new ClassPathXMLApplicationContext("spring-config-v2");
//        PetStore petStore = (PetStore) cxt.getBean("petStore");
        DefaultBeanFactory beanFactory = new DefaultBeanFactory();
        XMLBeanDefinitionReader beanDefReader =
                new DefaultXMLBeanDefinitionReader(beanFactory);
        beanDefReader.loadBeanDefinition(
                new ClassPathResource("spring-config-v2.xml"));
        BeanDefinition bd = beanFactory.getBeanDefinition("petStore");
        List<PropertyValue> pvs = bd.getPropValues();
        Assert.assertTrue(pvs.size() == 2);
        {
            PropertyValue pv = this.getPropVal(pvs, "accountDao");
            Assert.assertNotNull(pv);
            Assert.assertNotNull(pv.getValue());
            Assert.assertTrue(pv.getValue() instanceof RuntimeBeanReference);
        }
        {
            PropertyValue pv = this.getPropVal(pvs, "itemDao");
            Assert.assertNotNull(pv);
            Assert.assertNotNull(pv.getValue());
            Assert.assertTrue(pv.getValue() instanceof RuntimeBeanReference);
        }
    }


    private PropertyValue getPropVal(List<PropertyValue> list, String name) {
        for (PropertyValue propVal : list) {
            if (propVal.getName().equals(name)) return propVal;
        }
        return null;
    }


}
