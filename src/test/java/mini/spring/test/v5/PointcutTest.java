package mini.spring.test.v5;

import mini.spring.aop.MethodMatcher;
import mini.spring.aop.aspectj.AspectJExpressionPointcut;
import mini.spring.test.entity.PetStore;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * Description: 1、测试程序如何识别PointCut所描述的Join Point
 * <p>              使用AspectJExpressionPointcut封装了AspectJ中的部分功能,
 * <p>              来判断beanClass中的方法是否与expression相匹配
 * <p>
 * Created by lzm on 2018/11/14.
 */
public class PointcutTest {


    /**
     * 判断给定的class的给定方法,与expression的描述是否相匹配
     */
    @Test
    public void testPointcut() throws Exception {

        String expression = "execution(* mini.spring.test.entity.*.placeOrder(..))";

        AspectJExpressionPointcut pc = new AspectJExpressionPointcut();

        pc.setExpression(expression);

        MethodMatcher mm = pc.getMethodMatcher();

        {
            Class<?> targetClass = PetStore.class;

            Method method = targetClass.getMethod("placeOrder", int.class);

            Assert.assertTrue(mm.matches(method));

        }

        {
            Class<?> targetClass = mini.spring.test.entity.PetStore.class;

            Method method = targetClass.getMethod("getAccountDao");

            Assert.assertTrue(!mm.matches(method));

        }

    }

}
