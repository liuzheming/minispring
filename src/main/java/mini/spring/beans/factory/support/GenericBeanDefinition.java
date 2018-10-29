package mini.spring.beans.factory.support;

import mini.spring.beans.BeanDefinition;
import mini.spring.beans.ConstructorArgument;
import mini.spring.beans.PropertyValue;

import java.util.ArrayList;
import java.util.List;

public class GenericBeanDefinition implements BeanDefinition {

    private String id;

    private String beanClassName;

    private String scope;

    private ConstructorArgument constructorArgument = new ConstructorArgument();

    private List<PropertyValue> propValues = new ArrayList<>();

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
        return SCOPE_SINGLETON.equalsIgnoreCase(scope);
    }


    public void setId(String id) {
        this.id = id;
    }


    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    @Override
    public List<PropertyValue> getPropValues() {
        return this.propValues;
    }

    @Override
    public ConstructorArgument getConstructorArgument() {
        return this.constructorArgument;
    }
}
