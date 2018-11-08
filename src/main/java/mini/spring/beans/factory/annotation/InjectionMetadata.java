package mini.spring.beans.factory.annotation;

import java.util.List;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-11-08.
 */
public class InjectionMetadata {

    private Class<?> targetClass;

    private List<InjectionElement> elements;

    public InjectionMetadata(Class<?> targetClass, List<InjectionElement> elements) {
        this.targetClass = targetClass;
        this.elements = elements;
    }

    public List<InjectionElement> getInjectionElement() {
        return elements;
    }

    public void inject(Object target) {
        if (elements == null && elements.size() <= 0) {
            return;
        }
        for (InjectionElement element : elements) {
            element.inject(target);
        }
    }

}
