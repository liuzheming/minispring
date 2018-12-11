package mini.spring.beans.factory.support;

import mini.spring.beans.factory.config.SingletonBeanRegistry;
import mini.spring.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-12-11.
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private Map<String, Object> singletonObjects = new ConcurrentHashMap<>(64);

    @Override
    public void registrySingleton(String beanName, Object singletonObject) {
        Assert.notNull(beanName, "'beanName' must not be null");
        Object obj = singletonObjects.get(beanName);
        if (obj != null) {
            throw new IllegalStateException("Could not registry object [" + singletonObject + "]" +
                    "under bean name '" + beanName + "': there is already object [" + obj + "] bound");
        }
        this.singletonObjects.put(beanName, singletonObject);

    }

    @Override
    public Object getSingleton(String beanName) {
        return this.singletonObjects.get(beanName);
    }
}
