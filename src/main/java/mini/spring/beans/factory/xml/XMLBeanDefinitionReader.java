package mini.spring.beans.factory.xml;

import mini.spring.beans.BeanDefinition;
import mini.spring.core.io.Resource;


public interface XMLBeanDefinitionReader {


    void registerBeanDefinition(String beanId, BeanDefinition beanDefinition);

    void loadBeanDefinition(Resource resource);

}
