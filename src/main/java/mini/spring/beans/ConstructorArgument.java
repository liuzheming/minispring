package mini.spring.beans;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-10-29 .
 */
public class ConstructorArgument {

    private final List<ValueHolder> argumentValues = new LinkedList<>();

    public ConstructorArgument() {
    }


    public List<ValueHolder> getArgumentValues() {
        return Collections.unmodifiableList(argumentValues);
    }

    public void clear() {
        this.argumentValues.clear();
    }

    public void addArgumentValue(ValueHolder valueHolder) {
        this.argumentValues.add(valueHolder);
    }

    public void addArgumentValue(Object obj) {
        this.addArgumentValue(new ValueHolder(obj));
    }

    public int size() {
        return this.argumentValues.size();
    }

    public boolean isEmpty() {
        return this.argumentValues.isEmpty();
    }

    public static class ValueHolder {
        private Object value;

        private String type;

        private String name;

        public ValueHolder(Object value) {
            this.value = value;
        }

        public ValueHolder(Object value, String type) {
            this.value = value;
            this.type = type;
        }

        public ValueHolder(Object value, String type, String name) {
            this.value = value;
            this.type = type;
            this.name = name;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


}
