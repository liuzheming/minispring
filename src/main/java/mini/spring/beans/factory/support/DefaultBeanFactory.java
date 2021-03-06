package mini.spring.beans.factory.support;

import mini.spring.beans.BeanDefinition;
import mini.spring.beans.PropertyValue;
import mini.spring.beans.SimpleTypeConverter;
import mini.spring.beans.TypeConverter;
import mini.spring.beans.factory.BeanCreationException;
import mini.spring.beans.factory.ConfigurableBeanFactory;
import mini.spring.beans.factory.config.DependencyDescriptor;
import mini.spring.util.ClassUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBeanFactory implements ConfigurableBeanFactory, BeanDefinitionRegistry {


    private final Map<String, BeanDefinition> beanDefMap = new ConcurrentHashMap<>();
    private final Map<String, Object> beanMap = new ConcurrentHashMap<>();
    private ClassLoader beanClassLoader;

    public DefaultBeanFactory() {
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


    private Object createBean(BeanDefinition bd) {
        Object bean = instantiateBean(bd);
        populateBean(bd, bean);
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
}
