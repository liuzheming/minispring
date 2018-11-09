package mini.spring.test.v3;

import mini.spring.beans.BeanDefinition;
import mini.spring.beans.factory.support.ConstructorResolver;
import mini.spring.beans.factory.support.DefaultBeanFactory;
import mini.spring.beans.factory.xml.XMLBeanDefinitionReader;
import mini.spring.beans.factory.xml.support.DefaultXMLBeanDefinitionReader;
import mini.spring.core.io.support.ClassPathResource;
import mini.spring.test.entity.PetStore;
import org.junit.Assert;
import org.junit.Test;

/**
 * Description: 2. 测试根据BeanDefinition内的Constructor信息创建生成Bean的功能
 * <p>
 * Created by lzm on  2018-10-29 .
 */
public class ConstructorResolverTest {


    @Test
    public void testResolveConstructor() {

        DefaultBeanFactory bf = new DefaultBeanFactory();
        XMLBeanDefinitionReader reader = new DefaultXMLBeanDefinitionReader(bf);
        reader.loadBeanDefinition(new ClassPathResource("spring-config-v3.xml"));
        BeanDefinition bd = bf.getBeanDefinition("petStore");

        ConstructorResolver resolver = new ConstructorResolver(bf);
        PetStore petStore = (PetStore) resolver.autowireConstructor(bd);

        Assert.assertEquals(1, petStore.getVersion());
        Assert.assertEquals("lzm", petStore.getOwner());
        Assert.assertNotNull(petStore.getAccountDao());
        Assert.assertNotNull(petStore.getItemDao());
    }

}
