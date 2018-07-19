package mini.spring.test.v1;

import mini.spring.core.io.Resource;
import mini.spring.core.io.support.ClassPathResource;
import mini.spring.core.io.support.FileSystemResource;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * Description:
 * <p>
 * Created by lzm on 2018/7/19.
 */
public class ResourceTest {

    @Test
    public void testClassPathResource() throws Exception {

        // 无论那种Resource的作用都是读取inputStream
        Resource resource = new ClassPathResource("spring-config-v1.xml");
        InputStream is = null;
        try {
            is = resource.getInputStream();
        } finally {
            if (is != null) is.close();
        }
        Assert.assertNotNull(is);
    }

    @Test
    public void testFileSystemResource() throws Exception {
        Resource resource = new FileSystemResource
                ("/Users/lzm/Documents/IEDA_Workspace/minispring/src/test/resources/spring-config-v1.xml");
        InputStream is = null;
        try {
            is = resource.getInputStream();
            System.out.println(resource.getDescription());
            Assert.assertNotNull(is);
        } finally {
            if (is != null) is.close();
        }
    }
}
