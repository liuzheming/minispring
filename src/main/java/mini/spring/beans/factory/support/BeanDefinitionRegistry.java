package mini.spring.beans.factory.support;

import mini.spring.beans.BeanDefinition;

public interface BeanDefinitionRegistry {

    BeanDefinition getBeanDefinition(String beanId);

    void registerBeanDefinition(String beanId, BeanDefinition beanDef);

}
