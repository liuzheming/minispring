package mini.spring.test.v6;

import mini.spring.context.ApplicationContext;
import mini.spring.context.support.ClassPathXMLApplicationContext;
import mini.spring.test.entity.v6.IPetStore;
import org.junit.Test;

/**
 * Description:
 * <p>
 * Created by lzm on 2018/12/15.
 */
public class ApplicationCtxV6 {

    @Test
    public void getJDKProxy() {


        ApplicationContext ctx = new ClassPathXMLApplicationContext("spring-config-v6.xml");

        IPetStore ps = (IPetStore) ctx.getBean("petStore");

        ps.placeOrder(1);
    }

}
