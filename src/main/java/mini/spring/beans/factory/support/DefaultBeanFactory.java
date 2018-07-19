package mini.spring.beans.factory.support;

import mini.spring.beans.BeanDefinition;
import mini.spring.beans.factory.BeanFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBeanFactory implements BeanFactory, BeanDefinitionRegistry {


    private final Map<String, BeanDefinition> beanDefMap = new ConcurrentHashMap<>();
    private final Map<String, Object> beanMap = new ConcurrentHashMap<>();

    public DefaultBeanFactory() {
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return beanDefMap.get(beanName);
    }

    @Override
    public Object getBean(String beanId) {
        BeanDefinition bd = beanDefMap.get(beanId);
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
        Object bean = null;
        try {
            Class clazz = Class.forName(bd.getBeanClassName());
            bean = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

//    @Override
//    public BeanDefinition getBeanDefinition(String beanId) {
//        return null;
//    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDef) {
        this.beanDefMap.put(beanDef.getId(), beanDef);
    }
}
