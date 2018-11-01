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
 * Description: 1. 测试通过读取Class文件，来获取类的元数据的功能,包括class本身的信息和class被标注的annotation信息
 * <p>
 * Created by lzm on  2018-10-30 .
 */
public class ClassReaderTest {


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
