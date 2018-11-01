package mini.spring.test.v4;

import mini.spring.core.annotation.ClassPathBeanDefinitionScanner;
import mini.spring.core.io.Resource;
import mini.spring.core.io.support.ClassPathResource;
import mini.spring.core.type.classreading.MetadataReader;
import mini.spring.core.type.classreading.SimpleMetadataReader;
import mini.spring.stereotype.Component;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-11-01 .
 */
public class MetadataReaderTest {


    @Test
    public void testReadMetadata() throws IOException {

        Resource resource = new ClassPathResource("mini/spring/test/entity/PetStore.class");
//        Resource resource = new ClassPathResource("mini/spring/test/entity/AccountDao.class");
        MetadataReader reader = new SimpleMetadataReader(resource);
        Assert.assertEquals("petStore",
                reader.getAnnotationMetadata()
                        .getAnnotationAttributes(Component.class.getName())
                        .getString("value"));


    }

}
