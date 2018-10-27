package mini.spring.test.v2;

import mini.spring.beans.SimpleTypeConverter;
import mini.spring.beans.TypeConverter;
import mini.spring.beans.factory.BeanFactory;
import mini.spring.beans.factory.support.BeanDefinitionRegistry;
import mini.spring.beans.factory.support.DefaultBeanFactory;
import mini.spring.beans.factory.xml.support.DefaultXMLBeanDefinitionReader;
import mini.spring.core.io.Resource;
import mini.spring.core.io.support.ClassPathResource;
import org.junit.Assert;
import org.junit.Test;

/**
 * Description: 测试向成员变量注入值时,将值转为成员变量的类型
 * <p>本测试用例,可分解为CustomNumberConverterTest、CustomBooleanConverterTest两个测试用例
 * <p>
 * Created by lzm on  2018-10-26 .
 */
public class TypeConverterTest {

    @Test
    public void testConvertStringToInt() {
        BeanFactory beanFactory = new DefaultBeanFactory();
        DefaultXMLBeanDefinitionReader reader =
                new DefaultXMLBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinition(new ClassPathResource("spring-config-v2.xml"));
        TypeConverter converter = new SimpleTypeConverter();
        Integer i = converter.convertIfNecessary("3", Integer.class);
        Assert.assertEquals(3, i.intValue());
    }

    public void testConvertStringToBoolean() {

    }

}
