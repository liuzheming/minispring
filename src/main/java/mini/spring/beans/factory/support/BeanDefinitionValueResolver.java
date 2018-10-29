package mini.spring.beans.factory.support;

import mini.spring.beans.factory.BeanFactory;
import mini.spring.beans.factory.config.RuntimeBeanReference;
import mini.spring.beans.factory.config.TypedStringValue;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-10-26 .
 */
public class BeanDefinitionValueResolver {

    private final BeanFactory beanFactory;

    public BeanDefinitionValueResolver(BeanFactory bf) {
        this.beanFactory = bf;
    }

    public Object resolveValueIfNecessary(Object value) {
        if (value instanceof RuntimeBeanReference) {
            RuntimeBeanReference ref = (RuntimeBeanReference) value;
            return beanFactory.getBean(ref.getBeanName());
        } else if (value instanceof TypedStringValue) {
            TypedStringValue val = (TypedStringValue) value;
            return val.getValue();
        } else {
            //TODO
            throw new RuntimeException("the value " + value + " has not implemented");
        }
    }


}
