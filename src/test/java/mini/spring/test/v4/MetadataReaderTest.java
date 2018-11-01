package mini.spring.test.v4;

import mini.spring.core.io.Resource;
import mini.spring.core.io.support.ClassPathResource;
import mini.spring.core.type.AnnotationMetadata;
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

        AnnotationMetadata amd = reader.getAnnotationMetadata();
        Assert.assertNotNull(amd);

        String component = Component.class.getName();
        Assert.assertTrue(amd.hasAnnotation(component));
        Assert.assertEquals("petStore",
                amd.getAnnotationAttributes(component).getString("value"));


    }

}
