package mini.spring.test.v4;

import mini.spring.beans.factory.annotation.AutowiredAnnotationProcessor;
import mini.spring.beans.factory.annotation.InjectionMetadata;
import mini.spring.beans.factory.config.DependencyDescriptor;
import mini.spring.beans.factory.support.DefaultBeanFactory;
import mini.spring.test.entity.AccountDao;
import mini.spring.test.entity.ItemDao;
import mini.spring.test.entity.PetStore;
import org.junit.Assert;
import org.junit.Test;

/**
 * Description: 8、测试向一个Bean实例中注入它所依赖的对象
 * <p>             AutowiredAnnotationProcessor封装了：
 * <p>             读取targetBean所需的依赖项，并生成相应的InjectionMetadata向targetBean的过程
 * <p>
 * <p>
 * Created by lzm on  2018-11-09.
 */
public class AutowiredAnnotationProcessorTest {


    /**
     * 用匿名内部类隐藏了factory已经测试过的细节
     * <p>
     * 创建AutowiredAnnotationProcessor，它依赖于factory
     * 然后根据targetBean的class生成对应的InjectionMetadata
     */
    @Test
    public void testAutowireProcessor() {

        PetStore petStore = new PetStore();

        DefaultBeanFactory factory = new DefaultBeanFactory() {
            public Object resolveDependency(DependencyDescriptor descriptor) {
                if (descriptor.getDependencyType().equals(AccountDao.class)) {
                    return new AccountDao();
                }
                if (descriptor.getDependencyType().equals(ItemDao.class)) {
                    return new ItemDao();
                }
                throw new RuntimeException("can't support types except AccountDao and ItemDao");
            }
        };


        AutowiredAnnotationProcessor processor = new AutowiredAnnotationProcessor();
        processor.setBeanFactory(factory);
        // build过程会负责生成Metadata内对应的Elements
        InjectionMetadata injectionMetadata = processor.buildAutowiringMetadata(PetStore.class);

        injectionMetadata.inject(petStore);

        Assert.assertNotNull(petStore.getAccountDao());
        Assert.assertNotNull(petStore.getItemDao());

    }


}
