package mini.spring.test.v4;

import mini.spring.beans.factory.annotation.AutowiredFieldElement;
import mini.spring.beans.factory.annotation.InjectionElement;
import mini.spring.beans.factory.annotation.InjectionMetadata;
import mini.spring.beans.factory.support.DefaultBeanFactory;
import mini.spring.beans.factory.xml.XMLBeanDefinitionReader;
import mini.spring.beans.factory.xml.support.DefaultXMLBeanDefinitionReader;
import mini.spring.core.io.Resource;
import mini.spring.core.io.support.ClassPathResource;
import mini.spring.test.entity.PetStore;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.LinkedList;

/**
 * Description: 7、使用InjectionMetadata对象封装了向targetBean注入依赖的过程
 * <p>             InjectionElement内调用factory.resolveDependency()来生成依赖对象
 * <p>             采用InjectionMetadata来封装注入过程，有什么好处？是为了确保注入的灵活性嘛？
 * <p>             InjectionMetadata的工作方式用了什么设计模式？
 * <p>
 * <p>
 * Created by lzm on  2018-11-08.
 */
public class InjectionMetadataTest {


    /**
     * 测试向指定的某个Bean注入Field。
     * <p>
     * 通过TDD逐步实现完整功能的过程，是从只支持特殊情况，逐步向覆盖一般情况演进的过程。
     */
    @Test
    public void testInjectMetadata() throws Exception {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XMLBeanDefinitionReader reader = new DefaultXMLBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("spring-config-v4.xml");
        reader.loadBeanDefinition(resource);

        Class<?> clazz = PetStore.class;
        LinkedList<InjectionElement> elements = new LinkedList<>();

        {

            Field f = PetStore.class.getDeclaredField("accountDao");
            InjectionElement element = new AutowiredFieldElement(f, true, factory);
            elements.add(element);
        }
        {

            Field f = PetStore.class.getDeclaredField("itemDao");
            InjectionElement element = new AutowiredFieldElement(f, true, factory);
            elements.add(element);
        }

        PetStore petStore = new PetStore();

        InjectionMetadata metadata = new InjectionMetadata(clazz, elements);
        metadata.inject(petStore);

        Assert.assertNotNull(petStore.getAccountDao());


    }


}
