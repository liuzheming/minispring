package mini.spring.test.v5;

import mini.spring.aop.MethodMatcher;
import mini.spring.aop.aspect.AspectJExpressionPointcut;
import mini.spring.test.entity.PetStore;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * Description: 1、测试程序如何识别PointCut所描述的Join Point
 * <p>
 * Created by lzm on 2018/11/14.
 */
public class PointcutTest {

    @Test
    public void testPointcut() throws Exception {

        String expression = "execution(* mini.spring.test.entity.*.placeOrder(..))";

        AspectJExpressionPointcut pc = new AspectJExpressionPointcut();

        pc.setExpression(expression);

        MethodMatcher mm = pc.getMethodMatcher();

        {
            Class<?> targetClass = PetStore.class;

            Method method = targetClass.getMethod("placeOrder");

            Assert.assertNotNull(mm.matches(method));

        }

        {
            Class<?> targetClass = mini.spring.test.entity.PetStore.class;

            Method method = targetClass.getMethod("getAccountDao");

            Assert.assertNotNull(mm.matches(method));

        }

    }

}
