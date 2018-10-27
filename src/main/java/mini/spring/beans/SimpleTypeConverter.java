package mini.spring.beans;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-10-26 .
 */
public class SimpleTypeConverter implements TypeConverter {


    public SimpleTypeConverter() {
    }

    /**
     * 成员变量的值都会被调用本方法,但是只有必要时才会做转换
     *
     * @param value 成员变量的value
     * @param clazz 成员变量的字节码对象
     * @param <T>   成员变量的类型
     * @return 经过转换后得到的值
     */
    public <T> T convertIfNecessary(Object value, Class<T> clazz) {
        if (value.getClass().equals(clazz)) {
            return (T) value;
//        } else if (value instanceof TypedStringValue) {
//            return (T) value;
        } else {
            // value的类型和clazz不同,此时value应当为string类型,需要将value转为clazz类型的变量
            String str = (String) value;

            return clazz.cast(str);
        }
    }


}
