package mini.spring.beans.factory.config;

import mini.spring.beans.BeansException;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-11-13.
 */
public interface BeanPostProcessor {


    Object beforeInitialization(Object bean, String beanName) throws BeansException;


    Object afterInitialization(Object bean, String beanName) throws BeansException;


}
