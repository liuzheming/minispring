package mini.spring.beans;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-10-26 .
 */
public interface TypeConverter {


    <T> T convertIfNecessary(Object value, Class<T> clazz);
}
