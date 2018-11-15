package mini.spring.test.tx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-11-15.
 */
public class TransactionMgr {

    private Logger logger = LoggerFactory.getLogger(TransactionMgr.class);

    public void start() {
        logger.info("start tx");
    }

    public void commit() {
        logger.info("commit tx");
    }

    public void rollback() {
        logger.info("rollback tx");
    }

}
