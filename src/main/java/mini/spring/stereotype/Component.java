package mini.spring.stereotype;

import java.lang.annotation.*;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-10-31 .
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {


    /**
     * The value may indicate a suggestion for a logical component name,
     * to be turned into a Spring bean in case of an autodetected component.
     *
     * @return the suggested component name, if any
     */
    String value() default "";

}
