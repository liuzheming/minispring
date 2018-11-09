package mini.spring.beans.factory.support;

import mini.spring.beans.BeanDefinition;
import mini.spring.beans.ConstructorArgument;
import mini.spring.beans.PropertyValue;

import java.lang.reflect.Constructor;
import java.util.List;

public class DefaultBeanDefinition implements BeanDefinition {

    @Override
    public String getBeanClassName() {
        return null;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public List<PropertyValue> getPropValues() {
        return null;
    }

    @Override
    public ConstructorArgument getConstructorArgument() {
        return null;
    }

    @Override
    public boolean hasConstructorArg() {
        return false;
    }

    @Override
    public Class<?> resolveBeanClass(ClassLoader classLoader) throws ClassNotFoundException {
        return null;
    }

    @Override
    public Class<?> getBeanClass() throws IllegalStateException {
        return null;
    }

    @Override
    public boolean hasBeanClass() {
        return false;
    }
}
