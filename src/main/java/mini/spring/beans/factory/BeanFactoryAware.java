package mini.spring.beans.factory;

import mini.spring.beans.BeansException;

/**
 * Description:
 * <p>
 * Created by lzm on 2018/12/9.
 */
public interface BeanFactoryAware {

    void setBeanFactory(BeanFactory factory) throws BeansException;

}
