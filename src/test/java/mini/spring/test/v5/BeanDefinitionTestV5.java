package mini.spring.test.v5;

import mini.spring.aop.aspect.AspectJBeforeAdvice;
import mini.spring.aop.config.AspectInstanceFactory;
import mini.spring.aop.config.MethodLocatingFactory;
import mini.spring.beans.BeanDefinition;
import mini.spring.beans.ConstructorArgument;
import mini.spring.beans.PropertyValue;
import mini.spring.beans.factory.config.RuntimeBeanReference;
import mini.spring.beans.factory.support.DefaultBeanFactory;
import mini.spring.test.tx.TransactionMgr;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Description: 6、测试BeanDefinitionReader读取xml文件内aop标签的内容
 * <p>                  ---Advice、Pointcut和他们所依赖的一些Bean---的注册信息并生成BeanDefinition
 * <p>
 * <p>
 * Created by lzm on  2018-12-12.
 */
public class BeanDefinitionTestV5 extends AbstractTestV5 {

    @Test
    public void testAOPBean() {


        DefaultBeanFactory bf = getBeanFactory("spring-config-v5.xml");

        // 检查名称为tx的Bean定义是否生成
        {
            BeanDefinition bd = bf.getBeanDefinition("tx");
            Assert.assertEquals(bd.getBeanClassName(), (TransactionMgr.class.getName()));
        }

        // 检查placeOrder是否正确生成
        {
            BeanDefinition bd = bf.getBeanDefinition("placeOrder");
            Assert.assertTrue(bd.isSynthetic());

            Assert.assertEquals(1, bd.getPropValues().size());
            PropertyValue pv = bd.getPropValues().get(0);
            Assert.assertEquals("expression", pv.getName());
            Assert.assertEquals("execution(* mini.spring.test.entity.*.placeOrder(..))", pv.getValue());
        }


        //检查AspectJBeforeAdvice
        {
            String name = AspectJBeforeAdvice.class.getName() + "#0";
            BeanDefinition bd = bf.getBeanDefinition(name);
            Assert.assertTrue(bd.getBeanClass().equals(AspectJBeforeAdvice.class));

            //这个BeanDefinition是“合成的”
            Assert.assertTrue(bd.isSynthetic());

            List<ConstructorArgument.ValueHolder> args = bd.getConstructorArgument().getArgumentValues();
            Assert.assertEquals(3, args.size());

            //构造函数第一个参数
            {
                BeanDefinition innerBeanDef = (BeanDefinition) args.get(0).getValue();
                Assert.assertTrue(innerBeanDef.isSynthetic());
                Assert.assertTrue(innerBeanDef.getBeanClass().equals(MethodLocatingFactory.class));

                List<PropertyValue> pvs = innerBeanDef.getPropValues();
                Assert.assertEquals("targetBeanName", pvs.get(0).getName());
                Assert.assertEquals("tx", pvs.get(0).getValue());
                Assert.assertEquals("methodName", pvs.get(1).getName());
                Assert.assertEquals("start", pvs.get(1).getValue());
            }

            //构造函数第二个参数
            {
                RuntimeBeanReference ref = (RuntimeBeanReference) args.get(2).getValue();
                Assert.assertEquals("placeOrder", ref.getBeanName());
            }

            //构造函数第三个参数
            {
                BeanDefinition innerBeanDef = (BeanDefinition) args.get(1).getValue();
                Assert.assertTrue(innerBeanDef.isSynthetic());
                Assert.assertTrue(innerBeanDef.getBeanClass().equals(AspectInstanceFactory.class));

                List<PropertyValue> pvs = innerBeanDef.getPropValues();
                Assert.assertEquals("aspectBeanName", pvs.get(0).getName());
                Assert.assertEquals("tx", pvs.get(0).getValue());

            }

        }

        //TODO 作业：检查另外两个Bean


    }


}
