package mini.spring.test.v5;

import mini.spring.aop.aspect.AspectJBeforeAdvice;
import mini.spring.aop.aspect.AspectJExpressionPointcut;
import mini.spring.aop.framework.AopConfig;
import mini.spring.aop.framework.AopConfigSupport;
import mini.spring.aop.framework.CglibProxyFactory;
import mini.spring.test.entity.PetStore;
import mini.spring.test.tx.TransactionMgr;
import org.junit.Test;

/**
 * Description: 测试CglibFactory--依赖于AspectConfig--生成对应的proxy
 * <p>
 * Created by lzm on  2018-11-21.
 */
public class CglibAopProxyTest {


    @Test
    public void test() throws NoSuchMethodException {

        TransactionMgr adviceObj = new TransactionMgr();
        PetStore targetObj = new PetStore();

        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        String expression = "execution(* org.litespring.service.v5.*.placeOrder(..))";
        pointcut.setExpression(expression);

        AspectJBeforeAdvice beforeAdvice = new AspectJBeforeAdvice(
                adviceObj.getClass().getMethod("start"),
                adviceObj,
                pointcut);


        AspectJBeforeAdvice afterAdvice = new AspectJBeforeAdvice(
                adviceObj.getClass().getMethod("commit"),
                adviceObj,
                pointcut);

        AopConfig config = new AopConfigSupport();
        config.addAdvice(beforeAdvice);
        config.addAdvice(afterAdvice);
        config.setTargetObject(targetObj);

        CglibProxyFactory proxyFactory = new CglibProxyFactory(config);

        PetStore proxy = (PetStore) proxyFactory.getProxy();

        proxy.placeOrder(1);

    }

}
