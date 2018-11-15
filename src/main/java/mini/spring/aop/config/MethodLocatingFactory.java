package mini.spring.aop.config;

import mini.spring.beans.BeanUtils;
import mini.spring.beans.factory.BeanFactory;
import mini.spring.beans.factory.NoSuchBeanDefinitionException;
import mini.spring.util.StringUtils;

import java.lang.reflect.Method;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-11-15.
 */
public class MethodLocatingFactory {

    private String targetBeanName;

    private String methodName;

    private Method method;

    public void setTargetBeanName(String targetBeanName) {
        this.targetBeanName = targetBeanName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Method getObject() {
        return method;
    }

    public void setBeanFactory(BeanFactory factory) {
        if (!StringUtils.hasText(this.methodName)) {
            throw new IllegalArgumentException("Property 'methodName' is required");
        }
        if (!StringUtils.hasText(this.targetBeanName)) {
            throw new IllegalArgumentException("Property 'targetBeanName' is required");
        }

        Class<?> beanClass;
        try {
            beanClass = factory.getType(targetBeanName);
        } catch (NoSuchBeanDefinitionException e) {
            throw new IllegalArgumentException("Can't determine type of bean with name '" + targetBeanName + "'");
        }
        if (beanClass == null) {
            throw new IllegalArgumentException("Can't determine type of bean with name '" + targetBeanName + "'");
        }

        this.method = BeanUtils.resolveSignature(this.methodName, beanClass);

        if (method == null) {
            throw new IllegalArgumentException("Unable to locate method [" + methodName
                    + "] on bean [" + targetBeanName + "]");
        }
    }


}
