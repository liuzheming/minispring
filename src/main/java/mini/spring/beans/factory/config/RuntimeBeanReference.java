package mini.spring.beans.factory.config;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-10-25 .
 */
public class RuntimeBeanReference {
    private final String name;

    public RuntimeBeanReference(String name) {
        this.name = name;
    }

    public String getBeanName() {
        return name;
    }
}
