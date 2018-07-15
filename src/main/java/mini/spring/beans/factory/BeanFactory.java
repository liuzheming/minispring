package mini.spring.beans.factory;

import java.io.InputStream;

public interface BeanFactory {


    BeanDefinition getBeanDefinition(String beanName);

    Object getBean(String beanName);

}
