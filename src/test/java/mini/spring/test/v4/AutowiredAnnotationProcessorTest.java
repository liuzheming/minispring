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
 * Description:
 * <p>
 * Created by lzm on  2018-11-09.
 */
public class AutowiredAnnotationProcessorTest {


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
