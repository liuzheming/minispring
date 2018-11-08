package mini.spring.beans.factory.annotation;

import mini.spring.beans.factory.config.AutowireCapableBeanFactory;

import java.lang.reflect.Member;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-11-08.
 */
public abstract class InjectionElement {

    protected Member member;

    protected AutowireCapableBeanFactory factory;

    public InjectionElement(Member member, AutowireCapableBeanFactory factory) {
        this.member = member;
        this.factory = factory;
    }

    abstract void inject(Object target);


}
