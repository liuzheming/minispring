package mini.spring.beans.factory.support;

import mini.spring.beans.BeanDefinition;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-11-01 .
 */
public interface BeanNameGenerator {

    /**
     * Generate a bean name for the given bean definition.
     */
    String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry);

}
