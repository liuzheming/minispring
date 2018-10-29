package mini.spring.context;

import mini.spring.beans.factory.ConfigurableBeanFactory;

/**
 * Description:
 * <p>
 * Created by lzm on 2018/7/19.
 */
public interface ApplicationContext extends ConfigurableBeanFactory {

    Object getBean(String beanId);

}
