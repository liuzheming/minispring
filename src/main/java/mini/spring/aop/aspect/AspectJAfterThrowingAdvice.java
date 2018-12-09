package mini.spring.aop.aspect;

import mini.spring.aop.config.AspectInstanceFactory;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

public class AspectJAfterThrowingAdvice extends AbstractAspectJAdvice {


    public AspectJAfterThrowingAdvice(Method adviceMethod,
                                      AspectInstanceFactory aspectInstanceFactory,
                                      AspectJExpressionPointcut pointcut) {
        super(adviceMethod, aspectInstanceFactory, pointcut);
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