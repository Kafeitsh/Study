package bank;

public class Account implements Comparable<Account> {

    /**
     * баланс счета
     */
    private long money;

    /**
     * номер счета
     */
    private String accNumber;

    /**
     * блокировка счета
     */
    private boolean isBlocked = false;

    public Account(long money, String accNumber) {
        this.money = money;
        this.accNumber = accNumber;
    }

    /**
     * метод реализует перевод средств со счета
     *
     * @param amount - сумма перевода
     */
    public void getTransaction(long amount) {
        if(!isBlocked) {
            this.money = money - amount;
        }
    }

    /**
     * метод реализует перевод средств на счет
     *
     * @param amount - сумма перевода
     */
    public void incomeTransaction(long amount) {
        if(!isBlocked) {
            this.money = money + amount;
        }
    }

    /**
     * @return возвращает текущий остаток на счете
     */
    public long getMoney() {
        return money;
    }

    /**
     * @return возвращает номер счета
     */
    public String getAccNumber() {
        return accNumber;
    }

    /**
     * выводит сообщение с информацией о блокировке счета
     */
    public void isBlockedInfo() {
        if(!isBlocked) {
            System.out.println("Счет не заблокирован");
        } else {
            System.out.println("Счет заблокирован");
        }
    }

    /**
     * метод реализующий блокировку счета
     *
     * @param blocked - блокировка
     */
    public void setBlocked(boolean blocked) {
        this.isBlocked = blocked;
    }

    /**
     * геттер на блокировку счета
     *
     * @return - возвращает состояние
     */
    public boolean isBlocked() {
        return isBlocked;
    }

    @Override
    public int compareTo(Account o) {
        return this.getAccNumber().compareTo(o.getAccNumber());
    }
}
