package mini.spring.beans;

public interface BeanDefinition {

    String SINGLETON = "singleton";

    String getBeanClassName();

    String getId();

    boolean isSingleton();


}
