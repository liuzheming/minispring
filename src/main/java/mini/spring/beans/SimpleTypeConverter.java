package mini.spring.beans;

import mini.spring.beans.factory.propertyeditors.CustomNumberEditor;
import mini.spring.util.ClassUtils;

import java.beans.PropertyEditor;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-10-26 .
 */
public class SimpleTypeConverter implements TypeConverter {


    private Map<Class<?>, PropertyEditor> defaultEditors;

    public SimpleTypeConverter() {
    }

    /**
     * 成员变量的值都会被调用本方法,但是只有必要时才会做转换
     *
     * @param value        成员变量的value
     * @param requiredType 成员变量的字节码对象
     * @param <T>          成员变量的类型
     * @return 经过转换后得到的值
     */
    public <T> T convertIfNecessary(Object value, Class<T> requiredType) {

        if (ClassUtils.isAssignableValue(requiredType, value)) {
            return (T) value;
        } else {
            if (value instanceof String) {
                PropertyEditor editor = findDefaultEditor(requiredType);
                try {
                    editor.setAsText((String) value);
                } catch (IllegalArgumentException e) {
                    throw new TypeMismatchException(value, requiredType);
                }
                return (T) editor.getValue();
            } else {
                throw new RuntimeException("TODO: can not convert value " + value +
                        " type " + requiredType);
            }
        }
//            if (value.getClass().equals(clazz)) {
//                return (T) value;
//        } else if (value instanceof TypedStringValue) {
//            return (T) value;
//            } else {
        // value的类型和clazz不同,此时value应当为string类型,需要将value转为clazz类型的变量
//                String str = (String) value;

//                return clazz.cast(str);
//            }
//        }
    }


    private PropertyEditor findDefaultEditor(Class<?> requiredType) {
        PropertyEditor editor = getDefaultEditor(requiredType);
        if (editor == null) {
            throw new RuntimeException("Editor for " + requiredType + " has not been implemented");
        }
        return editor;
    }

    public PropertyEditor getDefaultEditor(Class<?> requiredType) {
        if (defaultEditors == null) {
            createDefaultEditors();
        }
        return defaultEditors.get(requiredType);
    }

    private void createDefaultEditors() {
        this.defaultEditors = new HashMap<>();
        this.defaultEditors.put(Integer.class, new CustomNumberEditor(Integer.class, true));
        this.defaultEditors.put(int.class, new CustomNumberEditor(Integer.class, true));
    }


}
