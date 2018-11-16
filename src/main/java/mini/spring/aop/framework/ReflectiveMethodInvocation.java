package mini.spring.aop.framework;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-11-16.
 */
public class ReflectiveMethodInvocation implements MethodInvocation {

    protected final Object targetObject;

    protected final Method targetMethod;

    protected Object[] args;

    protected final List<MethodInterceptor> interceptors;

    private int currentInterceptor = -1;


    public ReflectiveMethodInvocation(Object targetObject, Method targetMethod, Object[] args, List<MethodInterceptor> interceptors) {
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.args = args;
        this.interceptors = interceptors;
    }

    @Override
    public Method getMethod() {
        return targetMethod;
    }

    @Override
    public Object[] getArguments() {
        return new Object[0];
    }

    @Override
    public Object proceed() throws Throwable {

        if (currentInterceptor == interceptors.size() - 1) {
            return invokeJointPoint();
        }

        currentInterceptor++;

        MethodInterceptor interceptor = interceptors.get(currentInterceptor);


        return interceptor.invoke(this);
    }

    protected Object invokeJointPoint() throws Throwable {
        return this.targetMethod.invoke(targetObject, args);
    }

    @Override
    public Object getThis() {
        return targetObject;
    }

    @Override
    public AccessibleObject getStaticPart() {
        return targetMethod;
    }
}
