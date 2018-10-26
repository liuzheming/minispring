package mini.spring.test.v2;

import mini.spring.beans.SimpleTypeConverter;
import mini.spring.beans.TypeConverter;
import org.junit.Assert;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-10-26 .
 */
public class TypeConverterTest {

    public void testConvertStringToInt() {
        TypeConverter converter = new SimpleTypeConverter();
        Integer i = converter.convertIfNecessary("3", Integer.class);
        Assert.assertEquals(3, i.intValue());
    }

    public void testConvertStringToBoolean() {

    }

}
