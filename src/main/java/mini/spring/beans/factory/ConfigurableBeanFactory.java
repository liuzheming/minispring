package mini.spring.beans.factory;

import mini.spring.beans.factory.config.AutowireCapableBeanFactory;

public interface ConfigurableBeanFactory extends AutowireCapableBeanFactory {

    void setBeanClassLoader(ClassLoader beanCLassLoader);

    ClassLoader getBeanClassLoader();

}
