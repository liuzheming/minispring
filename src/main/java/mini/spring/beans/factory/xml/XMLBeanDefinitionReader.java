package mini.spring.beans.factory.xml;

import mini.spring.beans.BeanDefinition;

public interface XMLBeanDefinitionReader {

    void registerBeanDefinition(BeanDefinition beanDefinition);

    void loadBeanDefinition(String configFile);

}
