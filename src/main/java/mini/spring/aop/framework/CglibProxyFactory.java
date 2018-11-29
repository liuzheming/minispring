package mini.spring.aop.framework;


import mini.spring.aop.Advice;
import mini.spring.util.Assert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cglib.core.CodeGenerationException;
import org.springframework.cglib.core.SpringNamingPolicy;
import org.springframework.cglib.proxy.*;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-11-26.
 */
public class CglibProxyFactory implements AopProxyFactory {

    private static final int AOP_PROXY = 0;
    private static final int INVOKE_TARGET = 1;
    private static final int NO_OVERRIDE = 2;
    private static final int DISPATCH_TARGET = 3;
    private static final int DISPATCH_ADVISED = 4;
    private static final int INVOKE_EQUALS = 5;
    private static final int INVOKE_HASHCODE = 6;


    protected static final Logger logger = LogManager.getLogger(CglibProxyFactory.class);

    protected final AopConfig config;

    private Object[] constructorArgs;

    private Class<?>[] constrctorArgTypes;

    public CglibProxyFactory(AopConfig config) {
        Assert.notNull(config, "AdvisedSupport must not be specified");
        if (config.getAdvices().size() == 0) {
            throw new AopConfigException("No advisors and no TargetSource specified");
        }

        this.config = config;
    }


    @Override
    public Object getProxy() {
        return getProxy(null);
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {

        if (logger.isDebugEnabled()) {
            logger.debug("Creating CGLIB proxy: source is  " + this.config.getTargetClass());
        }

        try {
            Class<?> rootClass = this.config.getTargetClass();

            Enhancer enhancer = new Enhancer();
            if (classLoader != null) {
                enhancer.setClassLoader(classLoader);
            }
            enhancer.setSuperclass(rootClass);
            enhancer.setNamingPolicy(SpringNamingPolicy.INSTANCE);
            enhancer.setInterceptDuringConstruction(false);

            Callback[] callbacks = getCallbacks(rootClass);
            Class<?>[] types = new Class<?>[callbacks.length];

            for (int i = 0; i < types.length; i++) {
                types[i] = callbacks[i].getClass();
            }

            enhancer.setCallbacks(callbacks);
            enhancer.setCallbackTypes(types);
            Object proxy = enhancer.create();

            return proxy;
        } catch (CodeGenerationException e) {
            throw new AopConfigException("Could not generate CGLIB subclass of class " +
                    "[" + this.config.getTargetClass() + "] :" +
                    " Common causes of this problem include using a final class or non-visible class", e);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Could not generate CGLIB subclass of class " +
                    "[" + this.config.getTargetClass() + "] :" +
                    " Common causes of this problem include using a final class or non-visible class", ex);
        } catch (Exception ex) {
            throw new AopConfigException("Unexpected AOP exception [targetClass=" + this.config.getTargetClass() +
                    "]", ex);
        }

    }


    private Callback[] getCallbacks(Class<?> rootClass) throws Exception {
        Callback aopInterceptor = new DynamicAdvisedInterceptor(this.config);

        // Callback targetInterceptor = new StaticUnadvisedExposedInterceptor(this.advised.getTargetObject());

        // Callback targetDispatcher = new StaticDispatcher();

        Callback[] callbacks = new Callback[]{
                aopInterceptor
        };
        return callbacks;
    }


    private static class DynamicAdvisedInterceptor implements MethodInterceptor, Serializable {

        private final AopConfig config;

        public DynamicAdvisedInterceptor(AopConfig config) {
            this.config = config;
        }


        @Override
        public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws
                Throwable {

            Object target = config.getTargetObject();

            List<Advice> chains = this.config.getAdvices(method);

            Object retVal;
            // Check if we only have one InvokerInterceptor: that is,
            // no real advice, but just reflective invocation of the target.
            if (chains.isEmpty() && Modifier.isPublic(method.getModifiers())) {
                // We can skip creating a MethodInvocation: just invoke the target directly.
                // Note that the final invoker must be an InvokerInterceptor , so we know
                // it dose nothing but a reflective operation on target, and no hot
                // swapping or fancy proxying.
                retVal = methodProxy.invoke(target, args);
            } else {
                List<org.aopalliance.intercept.MethodInterceptor> interceptors =
                        new ArrayList<>();
                interceptors.addAll(chains);

                // We need to create a method invocation...
                retVal = new ReflectiveMethodInvocation(target, method, args, interceptors).proceed();
            }

            // retVal = processReturnType(proxy, target, method, retVal);
            return retVal;
        }
    }


    private static class ProxyCallbackFilter implements CallbackFilter {


        private final AopConfig config;

        ProxyCallbackFilter(AopConfig config) {
            this.config = config;
        }


        @Override
        public int accept(Method method) {
            // note this is a simplified version
            return AOP_PROXY;
        }
    }


}
