package mini.spring.test.entity;

import mini.spring.stereotype.Component;

@Component(value = "petStore")
public class PetStore {

    private AccountDao accountDao;

    private ItemDao itemDao;

    private String owner;

    private int version;

    public PetStore() {

    }

    public PetStore(AccountDao accountDao, ItemDao itemDao, String owner, int version) {
        this.accountDao = accountDao;
        this.itemDao = itemDao;
        this.owner = owner;
        this.version = version;
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
