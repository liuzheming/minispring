package mini.spring.aop.framework;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-11-26.
 */
public class AopConfigException extends RuntimeException {

    public AopConfigException(String msg) {
        super(msg);
    }

    public AopConfigException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
