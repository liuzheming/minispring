package mini.spring.test.v4;

import mini.spring.beans.factory.support.DefaultBeanFactory;
import mini.spring.beans.factory.xml.XMLBeanDefinitionReader;
import mini.spring.beans.factory.xml.support.DefaultXMLBeanDefinitionReader;
import mini.spring.core.annotation.ClassPathBeanDefinitionScanner;
import mini.spring.core.io.Resource;
import mini.spring.core.io.support.ClassPathResource;
import mini.spring.core.io.support.PackageResourceLoader;
import org.junit.Test;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-11-01 .
 */
public class ClassPathBeanDefinitionScannerTest {

    @Test
    public void testBeanDefinition() throws Exception {

        DefaultBeanFactory beanFactory = new DefaultBeanFactory();
        String basePackage = "mini.spring.test.entity";

        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(beanFactory);
        scanner.doScan(basePackage);


        XMLBeanDefinitionReader reader = new DefaultXMLBeanDefinitionReader(beanFactory);
        PackageResourceLoader loader = new PackageResourceLoader();
        Resource[] resources = loader.getResources("mini.spring.test.entity");
        reader.loadBeanDefinition(new ClassPathResource("spring-config-v4.xml"));

    }

}
