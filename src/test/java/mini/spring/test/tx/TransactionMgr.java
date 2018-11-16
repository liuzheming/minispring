package mini.spring.test.tx;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-11-15.
 */
public class TransactionMgr {

    private Logger logger = LogManager.getLogger(TransactionMgr.class);

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
