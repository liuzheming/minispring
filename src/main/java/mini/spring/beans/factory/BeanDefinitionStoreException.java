package mini.spring.beans.factory;

import mini.spring.beans.BeansException;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-11-01 .
 */
public class BeanDefinitionStoreException extends BeansException {

    public BeanDefinitionStoreException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
