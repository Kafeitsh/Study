package bank;

import java.util.HashMap;
import java.util.Random;

public class Bank {

    // реализовано изначально

    private final Random random = new Random();
    private final int TO_CHECK = 50000;
    private HashMap<String, Account> accounts = new HashMap<>();
    private boolean legal;

    public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
            throws InterruptedException {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    //=========================================================================================//

    /**
     * TODO: реализовать метод. Метод переводит деньги между счетами.
     * Если сумма транзакции > 50000, то после совершения транзакции,
     * она отправляется на проверку Службе Безопасности – вызывается
     * метод isFraud. Если возвращается true, то делается блокировка
     * счетов (как – на ваше усмотрение)
     */
    public synchronized void transfer(String fromAccountNum, String toAccountNum, long amount) throws InterruptedException {

        // информация о текущей операции
        System.out.println("Выполняется перевод денежных средств в размере " + amount +
                " рублей со счета №" + accounts.get(fromAccountNum).getAccNumber() +
                " на счет №" + accounts.get(toAccountNum).getAccNumber() + "....");
        System.out.println("Баланс счета №" + fromAccountNum + " до перевода равен " +
                getBalance(fromAccountNum) + " рублей");
        System.out.println("Баланс счета №" + toAccountNum + " до перевода равен " +
                getBalance(toAccountNum) + " рублей");

        if (amount > accounts.get(fromAccountNum).getMoney()) {

            // если сумма перевода превышает остаток средств на счете - выводится соотв сообщение
            System.out.println("Недостаточно средств на счете №" + accounts.get(fromAccountNum).getAccNumber() + "!");

        } else if (amount > TO_CHECK) {

            // если сумма превышает порог, установленный безопасным - реализуется метод проверки операции СБ
            System.out.println("Операция проверяется службой безопасности....");
            legal = isFraud(fromAccountNum, toAccountNum, amount);

            // если результат проверки положительный - операция соверщается, если отрицательный - счет блокируется
            if (legal) {
                successfulTransfer(fromAccountNum, toAccountNum, amount);
            } else {
                accountsBlock(fromAccountNum, toAccountNum);
            }

        } else {

            // если сумма не превышает порог, установленный безопасным - операция совершается
            successfulTransfer(fromAccountNum, toAccountNum, amount);

        }

        // после завершения выполнения операции выводятся информационные сообщения
        System.out.println("Баланс счета №" + fromAccountNum + " после перевода равен " +
                getBalance(fromAccountNum) + " рублей");
        System.out.println("Баланс счета №" + toAccountNum + " после перевода равен " +
                getBalance(toAccountNum) + " рублей");
        System.out.println("=========================================================");
    }

    /**
     * TODO: реализовать метод. Возвращает остаток на счёте.
     */
    public long getBalance(String accountNum) {
        return accounts.get(accountNum).getMoney();
    }

    /**
     * Метод добавления счетов в банк
     */
    public void addAccount(Account account) {
        accounts.put(account.getAccNumber(), account);
    }

    /**
     * Метод блокировки счетов
     * @param fromAccountNum - номер счета, с которого производится перевод
     * @param toAccountNum - номер счета, на который производится перевод
     */
    private void accountsBlock(String fromAccountNum, String toAccountNum) {
        accounts.get(fromAccountNum).setBlocked(true);
        accounts.get(toAccountNum).setBlocked(true);
        System.out.println("Счета №" + accounts.get(fromAccountNum).getAccNumber() + " & №"
                + accounts.get(toAccountNum).getAccNumber() + " заблокированы по подозрению в мошенничестве!");
    }

    /**
     * Метод успешного завершения операции
     */
    private synchronized void successfulTransfer(String fromAccountNum, String toAccountNum, long amount) {

        // если счет, с которого хотим выполнить перевод, пустой - ждем пока пополнится
        if (getBalance(fromAccountNum) == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // если ни один из счетов не заблокирован - переводим средства, выводим сообщение об успешном завершении
        // если любой из счетов заблокирован СБ - выводим сообщение о неудаче
        if (!accounts.get(fromAccountNum).isBlocked() && !accounts.get(toAccountNum).isBlocked()) {

            accounts.get(fromAccountNum).getTransaction(amount);
            accounts.get(toAccountNum).incomeTransaction(amount);

            System.out.println("Операция завершена успешно!");

        } else {

            System.out.println("Счет заблокирован, операция недоступна!");

        }
        notify();
    }

    /**
     * Метод возвращает список счетов банка
     */
    public HashMap<String, Account> getAccounts() {
        return accounts;
    }

    /**
     * Метод возвращает один счет банка по номеру счета
     */
    public Account getAccount(String accNumber) {
        return accounts.get(accNumber);
    }
}
