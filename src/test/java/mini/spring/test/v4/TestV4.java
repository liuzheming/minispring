package mini.spring.test.v4;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-11-13.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ApplicationTestV4.class,
        AutowiredAnnotationProcessorTest.class,
        ClassPathBeanDefinitionScannerTest.class,
        ClassReaderTest.class,
        DependencyDescriptorTest.class,
        InjectionMetadataTest.class,
        MetadataReaderTest.class,
        PackageResourceLoaderTest.class,
        XmlBeanDefinitionReaderTest.class
})
public class TestV4 {
}
