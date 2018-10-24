package mini.spring.beans.factory.support;

import mini.spring.beans.BeanDefinition;
import mini.spring.beans.PropertyValue;

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
}
