package mini.spring.beans.factory.support;

import mini.spring.beans.BeanDefinition;
import mini.spring.beans.ConstructorArgument;
import mini.spring.beans.SimpleTypeConverter;
import mini.spring.beans.TypeConverter;
import mini.spring.beans.factory.BeanCreationException;
import mini.spring.beans.factory.ConfigurableBeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.List;


/**
 * Description:
 * <p>
 * Created by lzm on  2018-10-29 .
 */
public class ConstructorResolver {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ConfigurableBeanFactory factory;

    public ConstructorResolver(ConfigurableBeanFactory bf) {
        this.factory = bf;
    }


    public Object autowireConstructor(BeanDefinition bd) {
        Constructor<?> constructorToUse = null;
        Object[] argsToUse = null;

        Class<?> beanClass = null;

        TypeConverter typeConverter = new SimpleTypeConverter();

        try {
            beanClass = this.factory.getBeanClassLoader().loadClass(bd.getBeanClassName());
        } catch (ClassNotFoundException e) {
            throw new BeanCreationException(bd.getId(), "Instantiation of bean failed, can't resolve class", e);
        }

        Constructor<?>[] candidates = beanClass.getConstructors();
        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(factory);

        for (int i = 0; i < candidates.length; i++) {
            if (candidates[i].getParameterCount() != bd.getConstructorArgument().size()) {
                continue;
            }
            Class[] paramTypes = candidates[i].getParameterTypes();
            argsToUse = new Object[paramTypes.length];

            boolean isMatch = this.valuesMatchTypes(paramTypes, argsToUse,
                    bd.getConstructorArgument().getArgumentValues(), valueResolver, typeConverter);

            if (isMatch) {
                constructorToUse = candidates[i];
                break;
            }
        }

        if (constructorToUse == null) {
            throw new BeanCreationException("can't find matched constructor", bd.getId());
        }

        try {
            return constructorToUse.newInstance(argsToUse);
        } catch (Exception e) {
            throw new BeanCreationException(bd.getId(), "create bean failed");
        }
    }


    /**
     * 注意只有XML里constructor-args的顺序和代码中构造方法参数顺序一致时才会认为是match的
     *
     * @param paramTypes    参数类型列表
     * @param argsToUse     在创建对象时向构造方法提供的参数，valueHolder里的value经过resolve和convert后得到
     * @param valueHolders  beanDefinition中保存的value信息
     * @param valueResolver value解析器
     * @param typeConverter value类型转换器
     * @return isMatch
     */
    private boolean valuesMatchTypes(Class<?>[] paramTypes, Object[] argsToUse,
                                     List<ConstructorArgument.ValueHolder> valueHolders,
                                     BeanDefinitionValueResolver valueResolver,
                                     TypeConverter typeConverter) {

        for (int i = 0; i < paramTypes.length; i++) {
            ConstructorArgument.ValueHolder valueHolder = valueHolders.get(i);
            Object originalValue = valueHolder.getValue();
            try {
                Object resolvedValue = valueResolver.resolveValueIfNecessary(originalValue);
                Object convertedValue = typeConverter.convertIfNecessary(resolvedValue, paramTypes[i]);
                argsToUse[i] = convertedValue;
            } catch (Exception e) {
                logger.error("fail match constructor to parameter", e);
                return false;
            }
        }
        return true;
    }

}
