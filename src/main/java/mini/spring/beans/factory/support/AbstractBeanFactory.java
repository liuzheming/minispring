package mini.spring.beans.factory.support;

import mini.spring.beans.BeanDefinition;
import mini.spring.beans.factory.BeanCreationException;
import mini.spring.beans.factory.ConfigurableBeanFactory;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-12-11.
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {
    protected abstract Object createBean(BeanDefinition bd) throws BeanCreationException;
}
