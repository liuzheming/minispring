package mini.spring.aop.framework;

import mini.spring.aop.Advice;
import mini.spring.util.Assert;
import mini.spring.util.ClassUtils;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * <p>
 * Created by lzm on 2018/12/15.
 */
public class JdkAopProxyFactory implements AopProxyFactory, InvocationHandler {


    /**
     * We use a static Log to avoid serialization issues
     */
    private static final Logger logger = LogManager.getLogger(JdkAopProxyFactory.class);


    private final AopConfig config;

    public JdkAopProxyFactory(AopConfig config) {
        Assert.notNull(config, "AdvisedSupport must not be null");
        if (config.getAdvices().size() == 0) {
            throw new AopConfigException("No advices specified");
        }
        this.config = config;
    }


    @Override
    public Object getProxy() {
        return getProxy(ClassUtils.getDefaultClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        if (logger.isDebugEnabled()) {
            logger.debug("Creating JDK dynamic proxy: target source is " + this.config.getTargetObject());
        }
        Class<?>[] proxiedInterfaces = config.getProxiedInterfaces();

        return Proxy.newProxyInstance(classLoader, proxiedInterfaces, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object retVal;
        Object object = config.getTargetObject();

        List<Advice> advices = config.getAdvices();

        if (advices.isEmpty()) {
            retVal = method.invoke(object, args);
        } else {
            List<MethodInterceptor> interceptors = new ArrayList<>();
            interceptors.addAll(advices);
            retVal = new ReflectiveMethodInvocation(object, method, args, interceptors).proceed();
        }


        return retVal;
    }
}
