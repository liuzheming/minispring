package mini.spring.aop.aspect;

import mini.spring.aop.Advice;
import mini.spring.aop.Pointcut;

import java.lang.reflect.Method;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-11-16.
 */
public abstract class AbstractAspectJAdvice implements Advice {

    private Method adviceMethod;

    private Object adviceObject;

    private AspectJExpressionPointcut pointcut;


    public AbstractAspectJAdvice(Method adviceMethod, Object adviceObject, AspectJExpressionPointcut pointcut) {
        this.adviceMethod = adviceMethod;
        this.adviceObject = adviceObject;
        this.pointcut = pointcut;
    }

    public void invokeAdviceMethod() throws Throwable {
        adviceMethod.invoke(adviceObject);
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    public Method getAdviceMethod() {
        return this.adviceMethod;
    }

}
