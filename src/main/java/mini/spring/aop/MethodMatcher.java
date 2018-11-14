package mini.spring.aop;

import java.lang.reflect.Method;

/**
 * Description:
 * <p>
 * Created by lzm on 2018/11/14.
 */
public interface MethodMatcher {

    boolean matches(Method method/*, Class<?> targetClass*/);

}
