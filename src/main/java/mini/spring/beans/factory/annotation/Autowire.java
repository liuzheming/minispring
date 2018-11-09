package mini.spring.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-11-09.
 */
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowire {

    boolean required() default true;

}
