package mini.spring.test.v4;

import mini.spring.core.io.support.ClassPathResource;
import mini.spring.core.type.classreading.ClassMetadataReadingVisitor;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.asm.ClassReader;

import java.io.IOException;
import java.io.InputStream;

/**
 * Description:
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

    }


}
