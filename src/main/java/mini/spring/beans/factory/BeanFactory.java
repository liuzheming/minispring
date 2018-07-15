package mini.spring.beans.factory;

import mini.spring.beans.BeanDefinition;

public interface BeanFactory {


    BeanDefinition getBeanDefinition(String beanName);

    Object getBean(String beanName);

}
