package mini.spring.test.v5;

import mini.spring.aop.aspect.AspectJBeforeAdvice;
import mini.spring.aop.framework.ReflectiveMethodInvocation;
import mini.spring.test.entity.PetStore;
import mini.spring.test.tx.TransactionMgr;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: 3、测试想targetBean绑定adviceMethod，并根据给定顺序调用targetMethod和adviceMethod
 * <p>
 * Created by lzm on  2018-11-16.
 */
public class ReflectMethodInvocationTest {


    @Test
    public void testMethodInvocation() throws Throwable {


        PetStore targetObject = new PetStore();
        Method targetMethod = PetStore.class.getDeclaredMethod("placeOrder", int.class);


        List<MethodInterceptor> interceptors = new ArrayList<>();
        Method adviceMethod = TransactionMgr.class.getMethod("start");
        TransactionMgr tx = new TransactionMgr();
        interceptors.add(new AspectJBeforeAdvice(adviceMethod, tx, null));

        MethodInvocation invocation = new ReflectiveMethodInvocation(
                targetObject,
                targetMethod,
                new Integer[]{-100000001},
                interceptors);

        invocation.proceed();


    }


}
