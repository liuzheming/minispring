package mini.spring.aop.aspect;

import mini.spring.aop.Pointcut;
import mini.spring.aop.config.AspectInstanceFactory;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-11-16.
 */
public class AspectJBeforeAdvice extends AbstractAspectJAdvice {


    public AspectJBeforeAdvice(Method adviceMethod,
                               AspectInstanceFactory aspectInstanceFactory,
                               AspectJExpressionPointcut pointcut) {
        super(adviceMethod, aspectInstanceFactory, pointcut);
    }

    public Object invoke(MethodInvocation mi) throws Throwable {
        this.invokeAdviceMethod();
        Object obj = mi.proceed();
        return obj;
    }
}
