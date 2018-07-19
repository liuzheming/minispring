package mini.spring.context.support;

import mini.spring.beans.factory.support.DefaultBeanFactory;
import mini.spring.beans.factory.xml.XMLBeanDefinitionReader;
import mini.spring.beans.factory.xml.support.DefaultXMLBeanDefinitionReader;
import mini.spring.context.ApplicationContext;
import mini.spring.core.io.Resource;
import mini.spring.core.io.support.FileSystemResource;

/**
 * Description:
 * <p>
 * Created by lzm on 2018/7/19.
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

    private DefaultBeanFactory beanFactory;

    public AbstractApplicationContext(String path) {
        beanFactory = new DefaultBeanFactory();
        XMLBeanDefinitionReader bdf = new DefaultXMLBeanDefinitionReader(beanFactory);
        bdf.loadBeanDefinition(getResource(path));
    }

    @Override
    public Object getBean(String beanId) {
        return beanFactory.getBean(beanId);
    }

    abstract Resource getResource(String path);
}
