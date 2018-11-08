package mini.spring.test.v4;

import mini.spring.beans.factory.config.DependencyDescriptor;
import mini.spring.beans.factory.support.DefaultBeanFactory;
import mini.spring.beans.factory.xml.XMLBeanDefinitionReader;
import mini.spring.beans.factory.xml.support.DefaultXMLBeanDefinitionReader;
import mini.spring.core.io.support.ClassPathResource;
import mini.spring.test.entity.AccountDao;
import mini.spring.test.entity.PetStore;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * Description: 6、到目前为止,已经可以生成一个没有经过注入的Bean了。
 * <p>             从本例开始将实现向Bean实例注入成员变量的功能。
 *
 * <p>             思路是在DefaultBeanFactory实现一个
 * <p>             resolveDependency(DependencyDescriptor dd):Object dependentBean
 * <p>             将生成targetBean所依赖的成员变量,DependencyDescriptor为依赖项的描述符
 * <p>
 * Created by lzm on 2018/11/7.
 */
public class DependencyDescriptorTest {


    /**
     * 测试AutowiredBeanFactory#resolvDependency()方法：
     * 对给定的依赖描述符，返回其所描述的值
     */
    @Test
    public void testResolveDependency() throws NoSuchFieldException {

        DefaultBeanFactory factory = new DefaultBeanFactory();

        XMLBeanDefinitionReader bdReader = new DefaultXMLBeanDefinitionReader(factory);
        bdReader.loadBeanDefinition(new ClassPathResource("spring-config-v4.xml"));

        Field field = PetStore.class.getDeclaredField("accountDao");

        DependencyDescriptor dd = new DependencyDescriptor(field, true);
        Object dependency = factory.resolveDependency(dd);
        Assert.assertTrue(dependency instanceof AccountDao);

    }


}
