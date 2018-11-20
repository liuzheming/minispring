package mini.spring.aop.aspect;

import mini.spring.aop.Pointcut;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-11-16.
 */
public class AspectJBeforeAdvice extends AbstractAspectJAdvice {


    public AspectJBeforeAdvice(Method adviceMethod, Object adviceObject, AspectJExpressionPointcut pointcut) {
        super(adviceMethod, adviceObject, pointcut);
    }

    public Object invoke(MethodInvocation mi) throws Throwable {
        this.invokeAdviceMethod();
        Object obj = mi.proceed();
        return obj;
    }
}
