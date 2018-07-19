package mini.spring.core.io.support;


import mini.spring.core.io.Resource;
import mini.spring.utils.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Description:
 * <p>
 * Created by lzm on 2018/7/19.
 */
public class FileSystemResource implements Resource {

    private final File file;

    public FileSystemResource(String path) {
        Assert.notNull(path, "path can not be null!");
        this.file = new File(path);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(file);
    }

    @Override
    public String getDescription() {
        return "file [" + file.getAbsolutePath() + "]";
    }
}
