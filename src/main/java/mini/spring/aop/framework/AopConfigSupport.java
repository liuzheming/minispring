package mini.spring.aop.framework;

import mini.spring.aop.Advice;
import mini.spring.util.Assert;

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

    public void addInterface(Class<?> intf) {
        Assert.notNull(intf, "interface must not be null");
        if (!intf.isInterface()) {
            throw new IllegalArgumentException("[" + intf.getName() + "] is not a interface");
        }
        if (!interfaces.contains(intf)) {
            this.interfaces.add(intf);
        }
    }


    @Override
    public Class<?>[] getProxiedInterfaces() {
        return interfaces.toArray(new Class[interfaces.size()]);
    }

    @Override
    public boolean isInterfaceProxied(Class<?> intf) {
        for (Class<?> proxyIntf : this.interfaces) {
            if (intf.isAssignableFrom(proxyIntf)) return true;
        }
        return false;
    }

    @Override
    public List<Advice> getAdvice() {
        return advices;
    }

    @Override
    public List<Advice> getAdvices(Method method) {
        List<Advice> result = new ArrayList<>();
        for (Advice advice : this.advices) {
            if (advice.getPointcut().getMethodMatcher().matches(method)) {
                result.add(advice);
            }
        }
        return result;
    }

    @Override
    public void setTargetObject(Object obj) {
        this.targetObject = obj;
    }
}
