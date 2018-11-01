package mini.spring.core.annotation;

import mini.spring.beans.BeanDefinition;
import mini.spring.beans.factory.annotation.AnnotatedBeanDefinition;
import mini.spring.beans.factory.support.BeanDefinitionRegistry;
import mini.spring.beans.factory.support.BeanNameGenerator;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-11-01 .
 */
public class AnnotationBeanNameGenerator implements BeanNameGenerator {


    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        if (definition instanceof AnnotatedBeanDefinition) {

        }


        return null;
    }
}
