package mini.spring.test.v4;

import mini.spring.core.io.Resource;
import mini.spring.core.io.support.PackageResourceLoader;
import org.junit.Assert;
import org.junit.Test;

/**
 * Description: 3、测试读取指定包路径下的所有class，转为Resource
 * <p>
 * Created by lzm on  2018-11-01 .
 */
public class PackageResourceLoaderTest {


    @Test
    public void testGetResources() throws Exception {

        PackageResourceLoader loader = new PackageResourceLoader();

        Resource[] resources = loader.getResources("mini.spring.test.entity");

        Assert.assertEquals(3, resources.length);

//        for (Resource res : resources) {
//            SimpleMetadataReader metaReader = new SimpleMetadataReader(res);
//            AnnotationMetadata amd = metaReader.getAnnotationMetadata();
//            amd.hasAnnotation()
//        }

    }

}
