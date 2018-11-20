package mini.spring.aop.aspect;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-11-16.
 */
public class AspectJAfterReturningAdvice extends AbstractAspectJAdvice {


    public AspectJAfterReturningAdvice(Method adviceMethod, Object adviceObject, AspectJExpressionPointcut pointcut) {
        super(adviceMethod, adviceObject, pointcut);
    }

    public Object invoke(MethodInvocation mi) throws Throwable {
        Object obj = mi.proceed();
        this.invokeAdviceMethod();
        return obj;
    }
}
