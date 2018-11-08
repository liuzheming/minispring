package mini.spring.beans.factory.annotation;

import mini.spring.beans.factory.BeanCreationException;
import mini.spring.beans.factory.config.AutowireCapableBeanFactory;
import mini.spring.beans.factory.config.DependencyDescriptor;
import mini.spring.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-11-08.
 */
public class AutowiredFieldElement extends InjectionElement {

    private boolean required;

    public AutowiredFieldElement(Field f, boolean required, AutowireCapableBeanFactory factory) {
        super(f, factory);
        this.required = required;
    }

    public Field getField() {
        return (Field) member;
    }

    @Override
    void inject(Object target) {
        Field field = getField();

        DependencyDescriptor dd = new DependencyDescriptor(field, true);
        Object fieldVal = factory.resolveDependency(dd);

        try {
            if (fieldVal != null) {
                ReflectionUtils.makeAccessible(field);
                field.set(target, fieldVal);
            }
        } catch (Throwable e) {
            throw new BeanCreationException("can't autowire field " + field, e);
        }

    }
}
