package mini.spring.test.v4;

import mini.spring.beans.factory.support.DefaultBeanFactory;
import mini.spring.context.annotation.ClassPathBeanDefinitionScanner;
import mini.spring.core.io.Resource;
import mini.spring.core.io.support.PackageResourceLoader;
import mini.spring.core.type.AnnotationMetadata;
import mini.spring.core.type.classreading.SimpleMetadataReader;
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

        PackageResourceLoader loader = new PackageResourceLoader();
        Resource[] resources = loader.getResources("mini.spring.test.entity");
//        XMLBeanDefinitionReader reader = new DefaultXMLBeanDefinitionReader(beanFactory);
//        reader.loadBeanDefinition(new ClassPathResource("spring-config-v4.xml"));


        for (Resource res : resources) {
            SimpleMetadataReader metaReader = new SimpleMetadataReader(res);
            AnnotationMetadata amd = metaReader.getAnnotationMetadata();
//            amd.getAnnotationAttributes()
        }

    }

}
