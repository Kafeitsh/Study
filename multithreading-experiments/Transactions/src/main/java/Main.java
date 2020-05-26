import bank.Account;
import bank.Bank;

import java.util.ArrayList;
import java.util.List;

/**
 * Учебное задание:
 *
 * Все счета хранятся внутри банка. Клиенты банка (их много) могут делать переводы между счетами,
 * запрашивать баланс по своему счету.
 * Все это происходит в highly concurrent (многопоточной) среде.
 *
 * При этом, транзакции на крупные суммы (> 50000) отправляются на проверку Службе Безопасности.
 * Можно считать, что таких транзакций не более 5% от всех.
 * За эту проверку отвечает отдельный и уже реализованный кем-то метод Bank.isFraud.
 *
 * Служба безопасности в Банке всего одна, работает медленно и не может обрабатывать более одной транзакции одновременно.
 * Проверка транзакции занимает у них 1000 мс.
 *
 * Если проверка выявила мошенничество, то необходимо заблокировать оба счета,
 * т.е. запретить любые изменения остатков.
 *
 * Нужно реализовать:
 *
 * Метод “transfer” класса Bank, который переводит деньги с одного счета на другой.
 * Если сумма транзакции > 50000, то после совершения транзакции,
 * она отправляется на проверку Службе Безопасности – вызывается метод isFraud.
 * Если возвращается true, то делается блокировка счетов (как – на ваше усмотрение).
 *
 * Метод getBalance класса Bank, который возвращает остаток на счете.
 *
 * Классы Account и Bank можно дорабатывать как угодно для решения задачи.
 * Кроме того, необходимо реализовать адекватный тест (или набор тестов)
 * для эмуляции реальной работы этих двух классов и проверки системы.
 */
public class Main {

    /**
     * константы для соблюдения условий
     */
    private static final int CHECKED_SUM = 52500;
    private static final int TO_CHECK = 50000;

    /**
     * количество операций
     */
    private static final int TRANSFERS_COUNT = 1000;
    private static List<String> numbers;

    public static void main(String[] args) {

        // создаем банк и наполняем его случайными счетами
        Bank bank = new Bank();
        numbers = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            String accNumber = numberRandomSet();
            Account account = new Account(moneyRandomSet(), accNumber);
            bank.addAccount(account);
            numbers.add(accNumber);
        }

        // каждый операция с транзакцией запускается в новом потоке
        for (int k = 0; k < TRANSFERS_COUNT; k++) {

            String numFrom = getRandomAccountNumber();
            String numTo = getRandomAccountNumber();
            long amount = amountRandomSet();

            new Thread(() -> {
                try {
                    bank.transfer(numFrom, numTo, amount);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    /**
     * Метод установки рандомного значения суммы изначального состояния средств на счете
     */
    public static int moneyRandomSet() {
        return (int) (Math.random() * 1000000);
    }

    /**
     * Метод установки рандомного номера счета
     */
    public static String numberRandomSet() {
        long accNumber = (long) (Math.random() * 10000000) + 10000000;
        return String.valueOf(accNumber);
    }

    /**
     * Метод установки рандомной суммы перевода
     */
    public static long amountRandomSet() {
        long money = (long) (Math.random() * CHECKED_SUM);
        if (money > TO_CHECK) {
            money = money + (long) (Math.random() * CHECKED_SUM * 10);
        }
        return money;
    }

    /**
     * Метод получения случайного номера счета из списка счетов принадлежащих банку
     */
    public static String getRandomAccountNumber() {
        return numbers.get((int)(Math.random() * numbers.size()));
    }
}
