package mini.spring.beans.factory.config;

import mini.spring.beans.factory.BeanFactory;

/**
 * Description: 扩展beanFactory，增加想targetBean自动注入Field的功能
 * <p>
 * Created by lzm on  2018-11-08.
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    Object resolveDependency(DependencyDescriptor depDesc);


}
