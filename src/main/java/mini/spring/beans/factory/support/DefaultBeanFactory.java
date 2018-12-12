package mini.spring.beans.factory.support;

import mini.spring.beans.*;
import mini.spring.beans.factory.BeanCreationException;
import mini.spring.beans.factory.BeanFactoryAware;
import mini.spring.beans.factory.ConfigurableBeanFactory;
import mini.spring.beans.factory.NoSuchBeanDefinitionException;
import mini.spring.beans.factory.config.BeanPostProcessor;
import mini.spring.beans.factory.config.DependencyDescriptor;
import mini.spring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import mini.spring.util.ClassUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBeanFactory extends AbstractBeanFactory implements ConfigurableBeanFactory, BeanDefinitionRegistry {


    private final Map<String, BeanDefinition> beanDefMap = new ConcurrentHashMap<>();
    private final Map<String, Object> beanMap = new ConcurrentHashMap<>();
    private ClassLoader beanClassLoader;
    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    public DefaultBeanFactory() {
    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor processor) {
        this.beanPostProcessors.add(processor);
    }

    @Override
    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }


    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return beanDefMap.get(beanName);
    }

    @Override
    public Object getBean(String beanId) {
        BeanDefinition bd;
        try {
            bd = beanDefMap.get(beanId);
        } catch (Exception e) {
            throw new RuntimeException("[beanId=" + beanId + "]", e);
        }
        if (bd == null) return null;
        if (bd.isSingleton()) {
            Object bean = beanMap.get(bd.getId());
            if (bean != null) {
                return bean;
            }
            bean = createBean(bd);
            beanMap.put(bd.getId(), bean);
            return bean;
        }
        return createBean(bd);
    }


    protected Object createBean(BeanDefinition bd) {
        Object bean = instantiateBean(bd);
        populateBean(bd, bean);
        bean = initializeBean(bd, bean);

        return bean;
    }

    private Object instantiateBean(BeanDefinition bd) {

        if (bd.hasConstructorArg()) {
            ConstructorResolver resolver = new ConstructorResolver(this);
            return resolver.autowireConstructor(bd);
        } else {
            ClassLoader cl = this.getBeanClassLoader();
            String beanClassName = bd.getBeanClassName();
            try {
                Class clazz = cl.loadClass(beanClassName);
                return clazz.newInstance();
            } catch (Exception e) {
                throw new BeanCreationException("create bean for " + beanClassName + " failed", e);
            }
        }
    }

    private void populateBean(BeanDefinition bd, Object bean) {


        for (BeanPostProcessor processor : this.beanPostProcessors) {
            if (processor instanceof InstantiationAwareBeanPostProcessor) {
                ((InstantiationAwareBeanPostProcessor) processor).postProcessPropertyValues(bean, bd.getId());
            }
        }


        List<PropertyValue> pvs = bd.getPropValues();
        if (pvs == null || pvs.isEmpty()) {
            return;
        }
        BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(this);

        try {
            for (PropertyValue pv : pvs) {
                Object result = resolver.resolveValueIfNecessary(pv.getValue());
                BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
                PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
                for (PropertyDescriptor pd : pds) {
                    if (pd.getName().equals(pv.getName())) {
                        Method setter = pd.getWriteMethod();
                        TypeConverter converter = new SimpleTypeConverter();
                        setter.invoke(bean, converter.convertIfNecessary(result, pd.getPropertyType()));
//                        if (pv.getValue() instanceof RuntimeBeanReference) {
//                            setter.invoke(bean, result);
//                        } else {
//                            result = new SimpleTypeConverter().convertIfNecessary(result, );
//                            setter.invoke(bean, result);
//                        }
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new BeanCreationException("Failed to obtain BeanInfo for class [" + bd.getBeanClassName() + "]", e);
        }

    }


    private Object initializeBean(BeanDefinition bd, Object bean) {
        invokeAutowireMethod(bean);
        if (!bd.isSynthetic()) {
            return applyBeanPostProcessorsAfterInitialization(bean, bd.getId());
        }
        return bean;
    }

    private Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws
            BeansException {
        Object result = existingBean;
        for (BeanPostProcessor processor : this.getBeanPostProcessors()) {
            result = processor.afterInitialization(result, beanName);
            if (result == null) {
                return result;
            }
        }
        return result;
    }


    private void invokeAutowireMethod(Object bean) {
        if (bean instanceof BeanFactoryAware) {
            ((BeanFactoryAware) bean).setBeanFactory(this);
        }
    }

//    @Override
//    public BeanDefinition getBeanDefinition(String beanId) {
//        return null;
//    }

    @Override
    public void registerBeanDefinition(String beanId, BeanDefinition beanDef) {
        this.beanDefMap.put(beanId, beanDef);
    }

    @Override
    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }

    @Override
    public ClassLoader getBeanClassLoader() {
        return (this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader());
    }


    /**
     * 根据依赖的的描述符，在factory内找到相应的Bean实例并返回
     *
     * @param depDesc 依赖的描述符
     * @return 被依赖的对象
     */
    @Override
    public Object resolveDependency(DependencyDescriptor depDesc) {
        Class<?> typeToMatch = depDesc.getDependencyType();
        // 根据classType找到对应的beanDefinition
        for (BeanDefinition beanDef : this.beanDefMap.values()) {
            resolveBeanClass(beanDef);
            if (!beanDef.hasBeanClass()) {
                return null;
            }
            if (beanDef.getBeanClass().isAssignableFrom(typeToMatch)) {
                return getBean(beanDef.getId());
            }
        }
        return null;
    }


    private void resolveBeanClass(BeanDefinition bd) {
        if (bd.hasBeanClass()) {
            return;
        }
        try {
            bd.resolveBeanClass(getBeanClassLoader());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("can't load class " + bd.getBeanClassName());
        }
    }


    public Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        BeanDefinition bd = this.getBeanDefinition(name);
        if (bd == null) {
            throw new NoSuchBeanDefinitionException(name);
        }
        resolveBeanClass(bd);
        return bd.getBeanClass();
    }


    public List<Object> getBeansByType(Class<?> cls) {
        List<String> ids = getBeanIdsByType(cls);
        List<Object> beans = new ArrayList<>();
        for (String id : ids) {
            beans.add(getBean(id));
        }
        return beans;
    }

    private List<String> getBeanIdsByType(Class<?> cls) throws NoSuchBeanDefinitionException {
        List<String> ids = new ArrayList<>();
        // 这个循环是自己第一次写出来的代码，
//        for (BeanDefinition bd : this.beanDefMap.values()) {
//            if (cls.isAssignableFrom(bd.getBeanClass())) {
//                ids.add(bd.getId());
//            }
//        }
        for (String name : this.beanDefMap.keySet()) {
            if (cls.isAssignableFrom(this.getType(name))) {
                ids.add(name);
            }
        }
        return ids;
    }
}
