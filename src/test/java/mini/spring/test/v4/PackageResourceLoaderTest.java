package mini.spring.test.v4;

import mini.spring.core.io.Resource;
import mini.spring.core.io.support.PackageResourceLoader;
import org.junit.Assert;
import org.junit.Test;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-11-01 .
 */
public class PackageResourceLoaderTest {


    @Test
    public void testGetResources() throws Exception {
        
        PackageResourceLoader loader = new PackageResourceLoader();

        Resource[] resources = loader.getResources("mini.spring.test.entity");

        Assert.assertEquals(3, resources.length);
    }

}
