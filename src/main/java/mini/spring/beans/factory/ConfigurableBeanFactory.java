package mini.spring.beans.factory;

public interface ConfigurableBeanFactory extends BeanFactory {

    void setBeanClassLoader(ClassLoader beanCLassLoader);

    ClassLoader getBeanClassLoader();

}
