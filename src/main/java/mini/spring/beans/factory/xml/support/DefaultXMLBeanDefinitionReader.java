package mini.spring.beans.factory.xml.support;

import mini.spring.beans.factory.support.BeanDefinitionRegistry;
import mini.spring.beans.factory.xml.XMLBeanDefinitionReader;
import mini.spring.utils.ClassUtils;

public class DefaultXMLBeanDefinitionReader implements XMLBeanDefinitionReader {

    private BeanDefinitionRegistry beanDefRegistry;

    public DefaultXMLBeanDefinitionReader(BeanDefinitionRegistry beanDefRegistry) {
        this.beanDefRegistry = beanDefRegistry;
    }

    private void loadXML() {
        ClassUtils.getDefaultClassLoader().getResourceAsStream();
        return;
    }

    @Override
    public void registerBeanDefinition() {

        beanDefRegistry.registerBeanDefinition();
    }
}
