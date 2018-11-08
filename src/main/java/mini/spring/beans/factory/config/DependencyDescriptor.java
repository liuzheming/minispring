package mini.spring.beans.factory.config;

import mini.spring.util.Assert;

import java.lang.reflect.Field;

/**
 * Description: 依赖的描述符，描述targetBean需要被注入的成员变量的信息
 * <p>
 * Created by lzm on  2018-11-08.
 */
public class DependencyDescriptor {

    private Field field;

    private boolean required;

    public DependencyDescriptor(Field field, boolean required) {
        Assert.notNull(field, "Field must not be null ");
        this.field = field;
        this.required = required;
    }

    public Class<?> getDependencyType() {
        if (this.field != null) {
            return field.getType();
        }
        throw new RuntimeException("only support field dependency ");
    }


    public boolean isRequired() {
        return required;
    }

}
