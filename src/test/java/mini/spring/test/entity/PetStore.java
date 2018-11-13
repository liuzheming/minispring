package mini.spring.test.entity;


import mini.spring.beans.factory.annotation.Autowire;
import mini.spring.stereotype.Component;

@Component
public class PetStore {

    @Autowire
    private AccountDao accountDao;

    @Autowire
    private ItemDao itemDao;

    private String owner;

    private int version;

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
