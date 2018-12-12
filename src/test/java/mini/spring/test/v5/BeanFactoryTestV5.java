package mini.spring.test.v5;

import mini.spring.aop.Advice;
import mini.spring.aop.aspectj.AspectJAfterReturningAdvice;
import mini.spring.aop.aspectj.AspectJAfterThrowingAdvice;
import mini.spring.aop.aspectj.AspectJBeforeAdvice;
import mini.spring.beans.factory.BeanFactory;
import mini.spring.test.entity.PetStore;
import mini.spring.test.tx.TransactionMgr;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Description: 7、测试：生成Advice等AOP相关的对象并注册到BeanFactory
 * <p>
 * Created by lzm on  2018-12-11.
 */
public class BeanFactoryTestV5 extends AbstractTestV5 {

    @Test
    public void testGetBeanByType() throws Exception {
        BeanFactory bf = getBeanFactory("spring-config-v5.xml");
        List<Object> advices = bf.getBeansByType(Advice.class);


        Assert.assertEquals(3, advices.size());


        {
            AspectJBeforeAdvice beforeAdvice = getAdvice(AspectJBeforeAdvice.class, advices);
            Assert.assertNotNull(beforeAdvice);
            Assert.assertEquals(beforeAdvice.getAdviceMethod(), TransactionMgr.class.getMethod("start"));
            beforeAdvice.getPointcut().getMethodMatcher().matches(PetStore.class.getMethod("placeOrder", int.class));
        }

        {
            AspectJAfterReturningAdvice commitAdvice = getAdvice(AspectJAfterReturningAdvice.class, advices);
            Assert.assertNotNull(commitAdvice);
            Assert.assertEquals(commitAdvice.getAdviceMethod(), TransactionMgr.class.getMethod("commit"));
            commitAdvice.getPointcut().getMethodMatcher().matches(PetStore.class.getMethod("placeOrder", int.class));
        }

        {
            AspectJAfterThrowingAdvice rollbackAdvice = getAdvice(AspectJAfterThrowingAdvice.class, advices);
            Assert.assertNotNull(rollbackAdvice);
            Assert.assertEquals(rollbackAdvice.getAdviceMethod(), TransactionMgr.class.getMethod("rollback"));
            rollbackAdvice.getPointcut().getMethodMatcher().matches(PetStore.class.getMethod("placeOrder", int.class));
        }


    }


    private <T> T getAdvice(Class<T> type, List<Object> advices) {
        for (Object advice : advices) {
            if (advice.getClass().equals(type)) {
                return (T) advice;
            }
        }
        return null;
    }

}
