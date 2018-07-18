package mini.spring.beans.factory.support;

import mini.spring.beans.BeanDefinition;

public class GenericBeanDefinition implements BeanDefinition {

    private String id;

    private String beanClassName;

    private String scope;

    public GenericBeanDefinition(String id, String beanClassName, String scope) {
        this.id = id;
        this.beanClassName = beanClassName;
        this.scope = scope;
    }


    @Override
    public String getBeanClassName() {
        return beanClassName;
    }


    public String getId() {
        return id;
    }

    @Override
    public boolean isSingleton() {
        return "singleton".equalsIgnoreCase(scope);
    }


    public void setId(String id) {
        this.id = id;
    }


    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }
}
