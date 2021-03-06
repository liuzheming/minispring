package mini.spring.context.support;

import mini.spring.core.io.Resource;
import mini.spring.core.io.support.ClassPathResource;

/**
 * Description:
 * <p>
 * Created by lzm on 2018/7/19.
 */
public class ClassPathXMLApplicationContext extends AbstractApplicationContext {


    public ClassPathXMLApplicationContext(String path) {
        super(path);
    }

    @Override
    Resource getResource(String path) {
        return new ClassPathResource(path);
    }

}
