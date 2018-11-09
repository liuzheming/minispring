package mini.spring.test.v4;

import mini.spring.context.annotation.AnnotationAttributes;
import mini.spring.core.io.support.ClassPathResource;
import mini.spring.core.type.classreading.AnnotationMetadataReadingVisitor;
import mini.spring.core.type.classreading.ClassMetadataReadingVisitor;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.asm.ClassReader;

import java.io.IOException;

/**
 * Description: 1. 读取读取单个Class文件，获取ClassMetadata、AnnotationMetadata信息
 * <p>
 * Created by lzm on  2018-10-30 .
 */
public class ClassReaderTest {


    /**
     * 读取一个指定的class，生成对应的classReader
     * 并使用visitor模式访问class文件的metadata
     */
    @Test
    public void testReadClassMetaData() throws IOException {


        ClassPathResource resource = new ClassPathResource("mini/spring/test/entity/PetStore.class");
        ClassReader reader = new ClassReader(resource.getInputStream());

        ClassMetadataReadingVisitor visitor = new ClassMetadataReadingVisitor();

        reader.accept(visitor, ClassReader.SKIP_DEBUG);

        Assert.assertEquals("mini.spring.test.entity.PetStore", visitor.getClassName());
        Assert.assertFalse(visitor.isAbstract());
        Assert.assertFalse(visitor.isFinal());
        Assert.assertFalse(visitor.isInterface());
    }


    /**
     * 获取class上标注的注解和每个注解中所设置的参数值
     */
    @Test
    public void testReadAnnotationMetadata() throws IOException {
        ClassPathResource resource = new ClassPathResource("mini/spring/test/entity/PetStore.class");
        ClassReader classReader = new ClassReader(resource.getInputStream());
        AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor();

        classReader.accept(visitor, ClassReader.SKIP_DEBUG);

        String annotation = "mini.spring.stereotype.Component";

        Assert.assertFalse(visitor.isAbstract());
        Assert.assertTrue(visitor.hasAnnotation(annotation));
        Assert.assertEquals(1, visitor.getAnnotationTypes().size());
        AnnotationAttributes attrs = visitor.getAnnotationAttributes(annotation);
        Assert.assertEquals("petStore", attrs.get("value"));
    }


}
