package mini.spring.beans;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-10-26 .
 */
public class BeansException extends RuntimeException {

    public BeansException(String message) {
        super(message);
    }

    public BeansException(String message, Throwable cause) {
        super(message, cause);
    }
}
