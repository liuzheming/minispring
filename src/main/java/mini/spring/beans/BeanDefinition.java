package mini.spring.beans;

import java.util.List;

public interface BeanDefinition {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    String SCOPE_DEFAULT = "";

    String getBeanClassName();

    String getId();

    boolean isSingleton();

    List<PropertyValue> getPropValues();

    ConstructorArgument getConstructorArgument();

    boolean hasConstructorArg();

    Class<?> resolveBeanClass(ClassLoader classLoader) throws ClassNotFoundException;

    Class<?> getBeanClass() throws IllegalStateException;

    boolean hasBeanClass();

    String getScope();

    void setScope(String scope);

    boolean isSynthetic();

    void setSynthetic(boolean synthetic);

}
