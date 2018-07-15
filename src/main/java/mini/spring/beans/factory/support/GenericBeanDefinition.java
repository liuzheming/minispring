package mini.spring.beans.factory.support;

import mini.spring.beans.BeanDefinition;

public class GenericBeanDefinition implements BeanDefinition {

    private String id;

    private String beanClassName;

    public GenericBeanDefinition(String id, String beanClassName) {
        this.id = id;
        this.beanClassName = beanClassName;
    }


    @Override
    public String getBeanClassName() {
        return beanClassName;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }
}
