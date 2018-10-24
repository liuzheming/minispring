package mini.spring.beans;

/**
 * Description: Bean属性定义
 * <p>
 * Created by lzm on 2018/10/24.
 */
public class PropertyValue {

    private String name;

    private Object value;

    private boolean converted = false;

    private Object convertedValue;

    public PropertyValue(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public synchronized boolean isConverted() {
        return this.converted;
    }
}
