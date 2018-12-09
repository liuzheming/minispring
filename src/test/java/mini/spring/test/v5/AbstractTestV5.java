package mini.spring.test.v5;

import mini.spring.aop.config.AspectInstanceFactory;
import mini.spring.beans.factory.BeanFactory;
import mini.spring.beans.factory.support.BeanDefinitionRegistry;
import mini.spring.beans.factory.support.DefaultBeanFactory;
import mini.spring.beans.factory.xml.XMLBeanDefinitionReader;
import mini.spring.beans.factory.xml.support.DefaultXMLBeanDefinitionReader;
import mini.spring.core.io.support.ClassPathResource;
import mini.spring.test.tx.TransactionMgr;

import java.lang.reflect.Method;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-12-04.
 */
public abstract class AbstractTestV5 {


    BeanFactory getBeanFactory(String configFile) {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XMLBeanDefinitionReader reader = new DefaultXMLBeanDefinitionReader(factory);
        reader.loadBeanDefinition(new ClassPathResource(configFile));
        return factory;
    }


    Method getAdviceMethod(String methodName) throws Exception {
        return TransactionMgr.class.getMethod(methodName);
    }

    AspectInstanceFactory getAspectInstanceFactory(String targetBeanName) {
        AspectInstanceFactory aiFactory = new AspectInstanceFactory();
        aiFactory.setAspectBeanName(targetBeanName);
        return aiFactory;
    }
}
