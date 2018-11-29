package mini.spring.aop.framework;

import mini.spring.aop.Advice;

import java.lang.reflect.Method;
import java.util.List;

public interface AopConfig {


    void addAdvice(Advice advice);

    Class<?> getTargetClass();

    Object getTargetObject();

    boolean getProxyTargetClass();

    Class<?>[] getProxiedInterfaces();

    boolean isInterfaceProxied(Class<?> intf);

    List<Advice> getAdvices();

    /**
     * 给定一个方法,找到其对应的Advice
     *
     * @param method 给定的方法
     * @return 对应的advice
     */
    List<Advice> getAdvices(Method method/*, Class<?> targetClass */);

    void setTargetObject(Object obj);


}
