package mini.spring.beans.factory;

public interface BeanFactory {


//    BeanDefinition getBeanDefinition(String beanId);

    Object getBean(String beanId);

    Class<?> getType(String name) throws NoSuchBeanDefinitionException;

}
