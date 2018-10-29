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

}
