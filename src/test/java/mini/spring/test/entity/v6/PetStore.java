package mini.spring.test.entity.v6;


import mini.spring.beans.factory.annotation.Autowire;
import mini.spring.stereotype.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Component(value = "petStore")
public class PetStore implements IPetStore {

    private Logger logger = LogManager.getLogger(PetStore.class);

    @Autowire
    private AccountDao accountDao;

    @Autowire
    private ItemDao itemDao;

    private String owner;

    private int version;

    public void placeOrder(int order) {
        logger.info("the order has bean placed to [{}]", order);
    }

    public void placeOrderError(int order) throws Exception {
        logger.info("the order has bean placed to [{}]", order);
        throw new RuntimeException();
    }


    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public ItemDao getItemDao() {
        return itemDao;
    }

    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    @Override
    public String toString() {
        return "PetStore{" +
                "accountDao=" + accountDao +
                ", itemDao=" + itemDao +
                '}';
    }
}
