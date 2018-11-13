package mini.spring.beans.factory;

import mini.spring.beans.factory.config.AutowireCapableBeanFactory;
import mini.spring.beans.factory.config.BeanPostProcessor;

import java.util.List;

public interface ConfigurableBeanFactory extends AutowireCapableBeanFactory {

    void setBeanClassLoader(ClassLoader beanCLassLoader);

    ClassLoader getBeanClassLoader();

    void addBeanPostProcessor(BeanPostProcessor processor);

    List<BeanPostProcessor> getBeanPostProcessors();

}
