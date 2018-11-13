package mini.spring.beans.factory.annotation;

import mini.spring.beans.BeansException;
import mini.spring.beans.factory.BeanCreationException;
import mini.spring.beans.factory.config.AutowireCapableBeanFactory;
import mini.spring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import mini.spring.core.annotation.AnnotationUtils;
import mini.spring.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-11-09.
 */
public class AutowiredAnnotationProcessor implements InstantiationAwareBeanPostProcessor {

    private AutowireCapableBeanFactory beanFactory;

    private Set<Class<? extends Annotation>> autowiredAnnotationTypes = new LinkedHashSet<>();

    private String requiredParameterName = "required";
    private boolean requiredParameterValue = true;


    public AutowiredAnnotationProcessor() {
        autowiredAnnotationTypes.add(Autowire.class);
    }


    public InjectionMetadata buildAutowiringMetadata(Class<?> clazz) {

        List<InjectionElement> elements = new LinkedList<>();
        Class<?> targetClazz = clazz;

        do {

            List<InjectionElement> currElements = new LinkedList<>();
            // 遍历clazz寻找需要注入的Field
            for (Field field : targetClazz.getDeclaredFields()) {
                Annotation ann = findAutowiredAnnotation(field);
                if (ann != null) {
                    if (Modifier.isStatic(field.getModifiers())) {
                        continue;
                    }
                    boolean required = determineRequiredStatus(ann);
                    // 为每个Field创建对应的InjectionElement
                    currElements.add(new AutowiredFieldElement(field, required, beanFactory));
                }
            }
            for (Method method : targetClazz.getDeclaredMethods()) {
                // TODO 注入方法
            }
            elements.addAll(0, currElements);
            targetClazz = targetClazz.getSuperclass();
        }
        while (targetClazz != null && targetClazz != Object.class);

        // 生成targetClazz对应的InjectionMetadata
        return new InjectionMetadata(targetClazz, elements);
    }


    private Annotation findAutowiredAnnotation(AccessibleObject ao) {
        for (Class<? extends Annotation> type : this.autowiredAnnotationTypes) {
            Annotation ann = AnnotationUtils.getAnnotation(ao, type);
            if (ann != null) return ann;
        }
        return null;
    }


    private boolean determineRequiredStatus(Annotation ann) {

        try {

            Method method = ReflectionUtils.findMethod(ann.annotationType(), this.requiredParameterName);
            if (method == null) {
                // Annotation like @Inject and @Value don't have a method (attribute) named "required"
                // -> default to required status
                return true;
            }
            return (this.requiredParameterValue == (Boolean) ReflectionUtils.invokeMethod(method, ann));
        } catch (Exception e) {
            // An Exception was thrown during reflective invocation of the required attribute
            // -> default to required status
            return true;
        }
    }

    public AutowireCapableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(AutowireCapableBeanFactory factory) {
        this.beanFactory = factory;
    }

    @Override
    public Object beforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public boolean afterInstantiation(Object bean, String beanName) throws BeansException {
        return false;
    }

    @Override
    public void postProcessPropertyValues(Object bean, String beanName) throws BeansException {
        InjectionMetadata metadata = buildAutowiringMetadata(bean.getClass());
        try {
            metadata.inject(bean);
        } catch (Exception e) {
            throw new BeanCreationException(beanName, "Injection of autowired dependency failed ", e);
        }
    }

    @Override
    public Object beforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object afterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }
}
