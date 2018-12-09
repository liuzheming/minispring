package mini.spring.aop.config;

import mini.spring.beans.BeansException;
import mini.spring.beans.factory.BeanFactory;
import mini.spring.beans.factory.BeanFactoryAware;
import mini.spring.util.StringUtils;

/**
 * Description:
 * <p>
 * Created by lzm on 2018/12/9.
 */
public class AspectInstanceFactory implements BeanFactoryAware {


    private String aspectBeanName;

    private BeanFactory factory;

    @Override
    public void setBeanFactory(BeanFactory factory) throws BeansException {
        this.factory = factory;
        if (!StringUtils.hasText(this.aspectBeanName)) {
            throw new IllegalArgumentException("'aspectBeanName' is required");
        }
    }

    public void setAspectBeanName(String aspectBeanName) {
        this.aspectBeanName = aspectBeanName;
    }


    public Object getAspectInstance() {
        return this.factory.getBean(aspectBeanName);
    }

}
