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
 * Description: 2、一次性读取ClassMetadata和AnnotationMetadata。
 * <p>             MetadataReader封装了读取单个class的ClassMetadata和AnnotationMetadata的功能
 * <p>
 * Created by lzm on  2018-11-01 .
 */
public class MetadataReaderTest {


    /**
     * 读取一个指定的class文件，
     * 并使用Visitor模式访问class上所带有的AnnotationMetadata信息
     * <p>
     * 本例使用SimpleMetadataReader封装了读取classMetadata和annotationMetadata的功能
     */
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
