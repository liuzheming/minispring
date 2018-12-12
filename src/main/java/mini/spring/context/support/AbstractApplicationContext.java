package mini.spring.context.support;

import mini.spring.aop.aspectj.AspectJAutoProxyCreator;
import mini.spring.beans.factory.ConfigurableBeanFactory;
import mini.spring.beans.factory.NoSuchBeanDefinitionException;
import mini.spring.beans.factory.annotation.AutowiredAnnotationProcessor;
import mini.spring.beans.factory.support.DefaultBeanFactory;
import mini.spring.beans.factory.xml.XMLBeanDefinitionReader;
import mini.spring.beans.factory.xml.support.DefaultXMLBeanDefinitionReader;
import mini.spring.context.ApplicationContext;
import mini.spring.core.io.Resource;
import mini.spring.util.ClassUtils;

import java.util.List;

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
        registerBeanPostProcessor(beanFactory);
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

    private void registerBeanPostProcessor(ConfigurableBeanFactory bf) {
        AutowiredAnnotationProcessor processor = new AutowiredAnnotationProcessor();
        processor.setBeanFactory(bf);
        bf.addBeanPostProcessor(processor);

        AspectJAutoProxyCreator aspectProxyCreator = new AspectJAutoProxyCreator();
        aspectProxyCreator.setConfigurableBeanFactory(bf);
        bf.addBeanPostProcessor(aspectProxyCreator);
    }

    public Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return this.beanFactory.getType(name);
    }

    public List<Object> getBeansByType(Class<?> cls) {
        return this.beanFactory.getBeansByType(cls);
    }

}
