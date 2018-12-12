package mini.spring.beans.factory.support;

import mini.spring.beans.BeanDefinition;
import mini.spring.beans.BeansException;
import mini.spring.beans.factory.BeanCreationException;
import mini.spring.beans.factory.BeanFactory;
import mini.spring.beans.factory.FactoryBean;
import mini.spring.beans.factory.config.RuntimeBeanReference;
import mini.spring.beans.factory.config.TypedStringValue;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-10-26 .
 */
public class BeanDefinitionValueResolver {

    private final AbstractBeanFactory beanFactory;

    public BeanDefinitionValueResolver(BeanFactory bf) {
        this.beanFactory = (AbstractBeanFactory) bf;
    }

    public Object resolveValueIfNecessary(Object value) {
        if (value instanceof RuntimeBeanReference) {
            RuntimeBeanReference ref = (RuntimeBeanReference) value;
            return beanFactory.getBean(ref.getBeanName());
        } else if (value instanceof TypedStringValue) {
            TypedStringValue val = (TypedStringValue) value;
            return val.getValue();
        } else if (value instanceof BeanDefinition) {
            BeanDefinition bd = (BeanDefinition) value;
            String innerBeanName = "(inner bean)" + bd.getBeanClassName() + "#" +
                    Integer.toHexString(System.identityHashCode(bd));
            return resolveInnerBean(innerBeanName, bd);
        } else {
            return value;
//            throw new RuntimeException("the value '" + value + "' has not implemented");
        }
    }

    private Object resolveInnerBean(String innerBeanName, BeanDefinition innerBd) {

        try {

            Object innerBean = this.beanFactory.createBean(innerBd);
            if (innerBean instanceof FactoryBean) {
                try {
                    return ((FactoryBean) innerBean).getObject();
                } catch (Exception e) {
                    throw new BeanCreationException(innerBeanName, "FactoryBean threw a exception on object creation", e);
                }
            } else {
                return innerBean;
            }
        } catch (BeansException ex) {
            throw new BeanCreationException(innerBeanName, "Can not create inner bean '" + innerBeanName + "' "
                    + (innerBd != null && innerBd.getBeanClassName() != null ? "of type [" + innerBd.getBeanClass() + "]" : ""),
                    ex);
        }
    }

}
