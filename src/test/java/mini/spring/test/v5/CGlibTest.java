package mini.spring.test.v5;

import mini.spring.test.entity.PetStore;
import mini.spring.test.tx.TransactionMgr;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.cglib.proxy.*;

import java.lang.reflect.Method;
import java.util.logging.Logger;

/**
 * Description: 4、测试使用CGLib动态生成 proxy
 * <p>
 * Created by lzm on  2018-11-16.
 */
public class CGlibTest {


    /**
     * 使用Enhancer对targetBean进行加强，enhancerBean需要知道targetBean、adviceBean
     * Question: how did enhancer knows that which method is the one should be enhanced ？
     */
    @Test
    public void test() {

        Enhancer enhancer = new Enhancer();

        enhancer.setSuperclass(PetStore.class);

        enhancer.setCallback(new TransactionInterceptor());

        PetStore ps = (PetStore) enhancer.create();

        // here，enhancer will enhances every method who belong to the TargetBean。
        ps.placeOrder(1);
        ps.getVersion();
    }

    public static class TransactionInterceptor implements MethodInterceptor {
        TransactionMgr tx = new TransactionMgr();

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            tx.start();
            Object result = methodProxy.invokeSuper(o, objects);
            tx.commit();
            return result;
        }
    }

    /**
     * 使用Filter来过滤掉不符合Pointcut描述的Method
     * Answer: By the Filter
     */
    @Test
    public void testFilter() {

        Enhancer enhancer = new Enhancer();

        enhancer.setSuperclass(PetStore.class);
        enhancer.setInterceptDuringConstruction(false);

        /* 向enhancer传入多个callback用于加强targetBean */
        Callback[] callbacks = new Callback[]{new TransactionInterceptor(), NoOp.INSTANCE};
        Class[] types = new Class[callbacks.length];
        for (int i = 0; i < callbacks.length; i++) {
            types[i] = callbacks[i].getClass();
        }

        enhancer.setCallbacks(callbacks);
        enhancer.setCallbackTypes(types);

        // 根据Filter过滤targetMethod
        enhancer.setCallbackFilter(e -> e.getName().startsWith("place") ? 0 : 1);

        PetStore ps = (PetStore) enhancer.create();
        Assert.assertNotNull(ps.getVersion());
        ps.placeOrder(1);

    }


}
