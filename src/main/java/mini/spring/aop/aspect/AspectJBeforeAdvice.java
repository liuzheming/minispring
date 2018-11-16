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
public class AspectJBeforeAdvice implements MethodInterceptor {

    private Method adviceMethod;

    private Object adviceObject;

    private AspectJExpressionPointcut pointcut;


    public AspectJBeforeAdvice(Method adviceMethod, Object adviceObject, AspectJExpressionPointcut pointcut) {
        this.adviceMethod = adviceMethod;
        this.adviceObject = adviceObject;
        this.pointcut = pointcut;
    }

    public void invokeAdviceMethod() throws Throwable {
        adviceMethod.invoke(adviceObject);
    }

    public Pointcut getPointcut() {
        return pointcut;
    }

    public Method getAdviceMethod() {
        return this.adviceMethod;
    }

    public Object invoke(MethodInvocation mi) throws Throwable {
        this.invokeAdviceMethod();
        Object obj = mi.proceed();
        return obj;
    }
}
