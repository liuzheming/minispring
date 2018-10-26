package mini.spring.context.support;

import mini.spring.beans.factory.support.DefaultBeanFactory;
import mini.spring.beans.factory.xml.XMLBeanDefinitionReader;
import mini.spring.beans.factory.xml.support.DefaultXMLBeanDefinitionReader;
import mini.spring.context.ApplicationContext;
import mini.spring.core.io.Resource;
import mini.spring.core.io.support.FileSystemResource;
import mini.spring.utils.ClassUtils;

/**
 * Description:
 * <p>
 * Created by lzm on 2018/7/19.
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

    private DefaultBeanFactory beanFactory;

    private ClassLoader beanClassLoader;

    public AbstractApplicationContext(String path) {
        beanFactory = new DefaultBeanFactory();
        XMLBeanDefinitionReader reader = new DefaultXMLBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinition(getResource(path));
        beanFactory.setBeanClassLoader(this.getBeanClassLoader());
    }

    @Override
    public Object getBean(String beanId) {
        return beanFactory.getBean(beanId);
    }

    abstract Resource getResource(String path);

    public ClassLoader getBeanClassLoader() {
        return (this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader());
    }

    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }
}
