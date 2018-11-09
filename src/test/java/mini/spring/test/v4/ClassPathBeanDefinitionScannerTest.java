package mini.spring.test.v4;

import mini.spring.beans.BeanDefinition;
import mini.spring.beans.factory.support.DefaultBeanFactory;
import mini.spring.context.annotation.AnnotationBeanDefinition;
import mini.spring.context.annotation.ClassPathBeanDefinitionScanner;
import mini.spring.stereotype.Component;
import org.junit.Assert;
import org.junit.Test;

/**
 * Description: 4、读取指定包路径下的所有class文件，并生成对应的BeanDefinition
 * <p>
 * Created by lzm on  2018-11-01.
 */
public class ClassPathBeanDefinitionScannerTest {


    /**
     * 使用ClassPathBeanDefinitionScanner封装了:
     * <p>
     * (1) 读取package下多个class文件的功能---packageResourceLoader
     * (2) 读取class文件的classMetadata、annotationMetadata的功能---MetadataReader
     * (3) 根据class文件的Metadata生成对应BeanDefinition的功能
     */
    @Test
    public void testBeanDefinition() throws Exception {

        DefaultBeanFactory beanFactory = new DefaultBeanFactory();
        String basePackage = "mini.spring.test.entity";

        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(beanFactory);
        scanner.doScan(basePackage);
        testGenerateBeanWithNoDependency(beanFactory);

    }


    public static void testGenerateBeanWithNoDependency(DefaultBeanFactory beanFactory) {
        {
            BeanDefinition bd = beanFactory.getBeanDefinition("petStore");
            Assert.assertTrue(bd instanceof AnnotationBeanDefinition);
            AnnotationBeanDefinition abd = (AnnotationBeanDefinition) bd;
            Assert.assertEquals("petStore",
                    abd.getMetadata().getAnnotationAttributes(Component.class.getName()).getString("value"));
        }
        {
            BeanDefinition bd = beanFactory.getBeanDefinition("itemDao");
            Assert.assertTrue(bd instanceof AnnotationBeanDefinition);
            AnnotationBeanDefinition abd = (AnnotationBeanDefinition) bd;
            Assert.assertNotNull(abd.getMetadata().getAnnotationAttributes(Component.class.getName()));
        }
        {
            BeanDefinition bd = beanFactory.getBeanDefinition("accountDao");
            Assert.assertTrue(bd instanceof AnnotationBeanDefinition);
            AnnotationBeanDefinition abd = (AnnotationBeanDefinition) bd;
            Assert.assertNotNull(abd.getMetadata().getAnnotationAttributes(Component.class.getName()));
        }
    }

}
