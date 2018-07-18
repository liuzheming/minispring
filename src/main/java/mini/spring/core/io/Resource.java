package mini.spring.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * Description:
 * <p>
 * Created by lzm on 2018/7/19.
 */
public interface Resource {

    InputStream getInputStream() throws IOException;

    String getDescription();

}
