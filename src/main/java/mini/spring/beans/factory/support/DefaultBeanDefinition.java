package mini.spring.beans.factory.support;

import mini.spring.beans.BeanDefinition;

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
}
