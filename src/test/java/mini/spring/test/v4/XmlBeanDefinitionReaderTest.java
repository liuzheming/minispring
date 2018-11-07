package mini.spring.test.v4;

import mini.spring.beans.factory.support.DefaultBeanFactory;
import mini.spring.beans.factory.xml.XMLBeanDefinitionReader;
import mini.spring.beans.factory.xml.support.DefaultXMLBeanDefinitionReader;
import mini.spring.core.io.support.ClassPathResource;
import org.junit.Test;

/**
 * Description: 5、读取spring-config.xml，读取其中需要扫描的包路径，并生成相应的BeanDefinition
 * <p>
 * Created by lzm on  2018-11-01 .
 */
public class XmlBeanDefinitionReaderTest {


    /**
     * 在beanDefinitionReader内嵌入beanDefinitionScanner
     * 于是支持读取spring-config.xml文件时扫描给定的base-package
     */
    @Test
    public void testGetBeanDefinition() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XMLBeanDefinitionReader bdReader = new DefaultXMLBeanDefinitionReader(factory);
        bdReader.loadBeanDefinition(new ClassPathResource("spring-config-v4.xml"));

        ClassPathBeanDefinitionScannerTest.test(factory);

    }
}
