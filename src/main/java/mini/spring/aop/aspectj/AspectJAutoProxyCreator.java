package mini.spring.aop.aspectj;

import mini.spring.aop.Advice;
import mini.spring.aop.MethodMatcher;
import mini.spring.aop.Pointcut;
import mini.spring.aop.framework.AopConfigSupport;
import mini.spring.aop.framework.AopProxyFactory;
import mini.spring.aop.framework.CglibProxyFactory;
import mini.spring.beans.BeansException;
import mini.spring.beans.factory.ConfigurableBeanFactory;
import mini.spring.beans.factory.config.BeanPostProcessor;
import mini.spring.util.ClassUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static mini.spring.util.ClassUtils.getAllInterfaces;

/**
 * Description:
 * <p>
 * Created by lzm on 2018/12/12.
 */
public class AspectJAutoProxyCreator implements BeanPostProcessor {
    private ConfigurableBeanFactory factory;

    public void setConfigurableBeanFactory(ConfigurableBeanFactory factory) {
        this.factory = factory;
    }

    @Override
    public Object beforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object afterInitialization(Object bean, String beanName) throws BeansException {

        // 如果这个Bean本身就是Advice或者其子类,就不要再生成动态代理了
        if (isInfrastructureClass(bean.getClass())) {
            return bean;
        }
        List<Advice> candidates = getCandidateAdvices(bean);
        if (candidates.isEmpty()) {
            return bean;
        }
        return createProxy(candidates, bean);
    }

    private Object createProxy(List<Advice> candidates, Object targetBean) {
        AopConfigSupport config = new AopConfigSupport();
        candidates.forEach(config::addAdvice);
        Set<Class> targetInterfaces = ClassUtils.getAllInterfacesForClassAsSet(targetBean.getClass());
        targetInterfaces.forEach(config::addInterface);
        config.setTargetObject(targetBean);

        AopProxyFactory proxyFactory = null;
        if (config.getProxiedInterfaces().length == 0) {
            proxyFactory = new CglibProxyFactory(config);
        } else {
            //TODO 需要实现JDK代理
        }
        return proxyFactory.getProxy();
    }


    private List<Advice> getCandidateAdvices(Object bean) {
        List<Object> advices = this.factory.getBeansByType(Advice.class);
        List<Advice> candidate = new ArrayList<>();
        for (Object o : advices) {
            Pointcut pointcut = ((Advice) o).getPointcut();
            if (canApply(pointcut, bean.getClass())) {
                candidate.add((Advice) o);
            }
        }
        return candidate;
    }

    private boolean canApply(Pointcut pointcut, Class<?> targetClass) {
        Set<Class> classes = ClassUtils.getAllInterfacesForClassAsSet(targetClass);
        classes.add(targetClass);
        MethodMatcher methodMatcher = pointcut.getMethodMatcher();
        for (Class clazz : classes) {
            for (Method method : clazz.getMethods()) {
                if (methodMatcher.matches(method/*, targetClass*/)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isInfrastructureClass(Class<?> clazz) {
        return clazz.isAssignableFrom(Advice.class);
    }
}
