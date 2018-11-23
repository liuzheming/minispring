package mini.spring.test.v5;

import mini.spring.aop.aspect.AspectJBeforeAdvice;
import mini.spring.aop.aspect.AspectJExpressionPointcut;
import mini.spring.test.entity.PetStore;
import mini.spring.test.tx.TransactionMgr;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-11-21.
 */
public class CglibAopProxyTest {

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




    }

}
