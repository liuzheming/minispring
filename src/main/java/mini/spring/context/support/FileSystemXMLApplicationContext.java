package mini.spring.context.support;

import mini.spring.core.io.Resource;
import mini.spring.core.io.support.FileSystemResource;

/**
 * Description:
 * <p>
 * Created by lzm on 2018/7/19.
 */
public class FileSystemXMLApplicationContext extends AbstractApplicationContext {

    public FileSystemXMLApplicationContext(String path) {
        super(path);
    }

    @Override
    public Resource getResource(String path) {
        return new FileSystemResource(path);
    }

}
