package mini.spring.test.v5;

import mini.spring.aop.aspect.AspectJBeforeAdvice;
import mini.spring.aop.aspect.AspectJExpressionPointcut;
import mini.spring.aop.config.AspectInstanceFactory;
import mini.spring.aop.framework.AopConfig;
import mini.spring.aop.framework.AopConfigSupport;
import mini.spring.aop.framework.CglibProxyFactory;
import mini.spring.beans.factory.BeanFactory;
import mini.spring.test.entity.PetStore;
import mini.spring.test.tx.TransactionMgr;
import org.junit.Before;
import org.junit.Test;

/**
 * Description: 5、测试CglibFactory--依赖于AspectConfig--生成对应的proxy
 * <p>
 * Created by lzm on  2018-11-21.
 */
public class CglibAopProxyTest extends AbstractTestV5 {

    private BeanFactory beanFactory;

    @Before
    public void before() {
        this.beanFactory = this.getBeanFactory("spring-config-v5.xml");
    }


    @Test
    public void test() throws NoSuchMethodException {

        TransactionMgr adviceObj = new TransactionMgr();

        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        String expression = "execution(* org.litespring.service.v5.*.placeOrder(..))";
        pointcut.setExpression(expression);

        AspectInstanceFactory aspectInstanceFactory= this.getAspectInstanceFactory("tx");
        aspectInstanceFactory.setBeanFactory(beanFactory);


        AspectJBeforeAdvice beforeAdvice = new AspectJBeforeAdvice(
                adviceObj.getClass().getMethod("start"),
                aspectInstanceFactory,
                pointcut);


        AspectJBeforeAdvice afterAdvice = new AspectJBeforeAdvice(
                adviceObj.getClass().getMethod("commit"),
                aspectInstanceFactory,
                pointcut);

        AopConfig config = new AopConfigSupport();
        config.addAdvice(beforeAdvice);
        config.addAdvice(afterAdvice);
        config.setTargetObject(new PetStore());

        CglibProxyFactory proxyFactory = new CglibProxyFactory(config);

        PetStore proxy = (PetStore) proxyFactory.getProxy();

        proxy.placeOrder(1);

    }

}
