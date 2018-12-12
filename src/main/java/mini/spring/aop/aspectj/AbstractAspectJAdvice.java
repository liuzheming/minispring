package mini.spring.aop.aspectj;

import mini.spring.aop.Advice;
import mini.spring.aop.Pointcut;
import mini.spring.aop.config.AspectInstanceFactory;

import java.lang.reflect.Method;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-11-16.
 */
public abstract class AbstractAspectJAdvice implements Advice {

    private Method adviceMethod;

    private AspectInstanceFactory aspectInstanceFactory;

    private AspectJExpressionPointcut pointcut;


    public AbstractAspectJAdvice(Method adviceMethod,
                                 AspectInstanceFactory aspectInstanceFactory,
                                 AspectJExpressionPointcut pointcut) {
        this.adviceMethod = adviceMethod;
        this.aspectInstanceFactory = aspectInstanceFactory;
        this.pointcut = pointcut;
    }

    public void invokeAdviceMethod() throws Throwable {
        adviceMethod.invoke(aspectInstanceFactory.getAspectInstance());
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    public Method getAdviceMethod() {
        return this.adviceMethod;
    }

}
