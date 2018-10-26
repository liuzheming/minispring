package mini.spring.test.entity;

public class PetStore {

    private AccountDao accountDao;

    private ItemDao itemDao;

    private String owner;

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
