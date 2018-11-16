package mini.spring.aop.aspect;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

public class AspectJAfterThrowingAdvice extends AbstractAspectJAdvice {


    public AspectJAfterThrowingAdvice(Method adviceMethod, Object adviceObject, AspectJExpressionPointcut pointcut) {

        super(adviceMethod, adviceObject, pointcut);
    }


    public Object invoke(MethodInvocation mi) throws Throwable {
        try {
            return mi.proceed();
        } catch (Throwable t) {
            invokeAdviceMethod();
            throw t;
        }
    }

}