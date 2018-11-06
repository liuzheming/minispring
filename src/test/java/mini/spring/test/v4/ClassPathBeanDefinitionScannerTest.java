package mini.spring.test.v4;

import mini.spring.beans.BeanDefinition;
import mini.spring.beans.factory.BeanFactory;
import mini.spring.beans.factory.support.DefaultBeanFactory;
import mini.spring.context.annotation.AnnotationBeanDefinition;
import mini.spring.context.annotation.ClassPathBeanDefinitionScanner;
import mini.spring.stereotype.Component;
import org.junit.Assert;
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
        test(beanFactory);

    }


    public static void test(DefaultBeanFactory beanFactory) {
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
