package mini.spring.beans.factory;

import mini.spring.beans.BeansException;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-10-26 .
 */
public class BeanCreationException extends BeansException {

    private String beanName;

    public BeanCreationException(String msg) {
        super(msg);
    }


    public BeanCreationException(String message, Throwable cause) {
        super(message, cause);
    }


    public BeanCreationException(String message, String beanName) {
        super("Error creating bean with name '" + beanName + "': " + message);
        this.beanName = beanName;
    }

    public BeanCreationException(String beanName, String message, Throwable cause) {
        this(message, beanName);
        initCause(cause);
    }

    public String getBeanName() {
        return this.beanName;
    }
}
