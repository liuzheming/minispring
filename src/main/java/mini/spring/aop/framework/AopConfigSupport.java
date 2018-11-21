package mini.spring.aop.framework;

import mini.spring.aop.Advice;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-11-21.
 */
public class AopConfigSupport implements AopConfig {


    private boolean proxyTargetClass = false;

    private Object targetObject = null;

    private List<Advice> advices = new ArrayList<>();

    private List<Class<?>> interfaces = new ArrayList<>();

    public AopConfigSupport() {

    }

    @Override
    public Class<?> getTargetClass() {
        return targetObject.getClass();
    }

    @Override
    public Object getTargetObject() {
        return targetObject;
    }

    @Override
    public boolean getProxyTargetClass() {
        return proxyTargetClass;
    }

    @Override
    public Class<?>[] getProxiedInterfaces() {
        return new Class[0];
    }

    @Override
    public boolean isInterfaceProxied() {
        return false;
    }

    @Override
    public List<Advice> getAdvice() {
        return null;
    }

    @Override
    public List<Advice> getAdvices(Method method) {
        return null;
    }

    @Override
    public void setTargetObject(Object obj) {

    }
}
