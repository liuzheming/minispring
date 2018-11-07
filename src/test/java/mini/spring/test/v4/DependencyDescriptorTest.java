package mini.spring.test.v4;

/**
 * Description: 6、到目前为止,已经可以生成一个没有经过注入的Bean了。
 * <p>             从本例开始将实现向Bean实例注入成员变量的功能。
 * <p>
 * <p>             思路是在DefaultBeanFactory实现一个
 * <p>             resolveDependency(DependencyDescriptor dd):Object dependentBean
 * <p>             将生成targetBean所依赖的成员变量,DependencyDescriptor为依赖项的描述符
 * <p>
 * Created by lzm on 2018/11/7.
 */
public class DependencyDescriptorTest {

    public void testResolveDependency() {

    }


}
