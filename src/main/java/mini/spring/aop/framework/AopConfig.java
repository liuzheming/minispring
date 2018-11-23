package mini.spring.aop.framework;

import mini.spring.aop.Advice;

import java.lang.reflect.Method;
import java.util.List;

public interface AopConfig {


    Class<?> getTargetClass();

    Object getTargetObject();

    boolean getProxyTargetClass();

    Class<?>[] getProxiedInterfaces();

    boolean isInterfaceProxied(Class<?> intf);

    List<Advice> getAdvice();

    List<Advice> getAdvices(Method method/*, Class<?> targetClass */);

    void setTargetObject(Object obj);


}
