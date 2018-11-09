package mini.spring.context;

import mini.spring.beans.factory.BeanFactory;

/**
 * Description:
 * <p>
 * Created by lzm on 2018/7/19.
 */
public interface ApplicationContext extends BeanFactory {

    Object getBean(String beanId);

}
