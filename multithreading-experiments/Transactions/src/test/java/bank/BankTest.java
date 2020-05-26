package bank;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BankTest extends TestCase {

    Bank bank;
    HashMap<String, Account> accounts;
    List<String> numbers;
    List<Thread> threads;
    long amount;
    long amount1;
    long amount2;
    long startMoney;
    String accNumber;
    Account accFrom1;
    Account accTo1;
    Account accFrom2;
    Account accTo2;
    Account accFrom3;
    Account accTo3;

    Account blockedAccFrom;
    Account blockedAccTo;

    @Override
    protected void setUp() {

        bank = new Bank();
        accounts = new HashMap<>();
        numbers = new ArrayList<>();
        threads = new ArrayList<>();

        for(int i =0; i < 100; i++) {
            startMoney = (long) (Math.random() * 1000000);
            accNumber = String.valueOf((long) (Math.random() * 1000000) + 1000000);
            accounts.put(accNumber, new Account(startMoney, accNumber));
            numbers.add(accNumber);
        }

        for (int k = 0; k < 1000; k++) {
            amount = (long) (Math.random() * 100000);
            String from = numbers.get((int) (Math.random() * numbers.size()));
            String to = numbers.get((int) (Math.random() * numbers.size()));
            threads.add(
                    new Thread(() ->  {
                        try {
                            bank.transfer(from, to, amount);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }));
        }

        accFrom1 = new Account(100000, "1");
        accTo1 = new Account(150000, "2");
        amount = 50000;
        accFrom2 = new Account(10000, "3");
        accTo2 = new Account(65000, "4");
        amount1 = 10000;
        accFrom3 = new Account(95000, "5");
        accTo3 = new Account(85000, "6");
        amount2 = 100000;

        // blocked
        blockedAccFrom = new Account(450000, "7");
        blockedAccTo = new Account(65000, "8");
        blockedAccFrom.setBlocked(true);
        blockedAccTo.setBlocked(true);

        bank.addAccount(accFrom1);
        bank.addAccount(accFrom2);
        bank.addAccount(accFrom3);
        bank.addAccount(accTo1);
        bank.addAccount(accTo2);
        bank.addAccount(accTo3);
        bank.addAccount(blockedAccFrom);
        bank.addAccount(blockedAccTo);
    }

    public void testTransfer() {
        try {
            bank.transfer("1", "2", amount);
            bank.transfer("3", "4", amount1);
            bank.transfer("4", "5", amount2);
            // blocked
            bank.transfer("1", "7", amount1);
            bank.transfer("7", "6", amount1);
            bank.transfer("7", "8", amount1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long accFrom1Balance = bank.getBalance(accFrom1.getAccNumber());
        long expectedAccFrom1Balance = 50000;

        long accTo1Balance = bank.getBalance(accTo1.getAccNumber());
        long expectedAccTo1Balance = 200000;

        //=============================================================

        long accFrom2Balance = bank.getBalance(accFrom2.getAccNumber());
        long expectedAccFrom2Balance = 0;

        long accTo2Balance = bank.getBalance(accTo2.getAccNumber());
        long expectedAccTo2Balance = 75000;

        //=============================================================

        long accFrom3Balance = bank.getBalance(accFrom3.getAccNumber());
        long expectedAccFrom3Balance = 95000;

        long accTo3Balance = bank.getBalance(accTo3.getAccNumber());
        long expectedAccTo3Balance = 85000;

        //=============================================================

        // blocked accounts

        long blockedAccFromBalance = bank.getBalance(blockedAccFrom.getAccNumber());
        long expectedBlockedAccFromBalance = 450000;

        long blockedAccToBalance = bank.getBalance(blockedAccTo.getAccNumber());
        long expectedBlockedAccToBalance = 65000;

        //=============================================================

        boolean isBlockedAccFrom = bank.getAccount(blockedAccFrom.getAccNumber()).isBlocked();

        boolean isBlockedAccTo = bank.getAccount(blockedAccTo.getAccNumber()).isBlocked();

        boolean isBlockedAcc = bank.getAccount(accFrom3.getAccNumber()).isBlocked();

        //=============================================================

        assertEquals(accFrom1Balance, expectedAccFrom1Balance);
        assertEquals(accTo1Balance, expectedAccTo1Balance);

        //=============================================================

        assertEquals(accFrom2Balance, expectedAccFrom2Balance);
        assertEquals(accTo2Balance, expectedAccTo2Balance);

        //=============================================================

        assertEquals(accFrom3Balance, expectedAccFrom3Balance);
        assertEquals(accTo3Balance, expectedAccTo3Balance);

        //=============================================================

        // blocked accounts

        assertEquals(blockedAccFromBalance, expectedBlockedAccFromBalance);
        assertEquals(blockedAccToBalance, expectedBlockedAccToBalance);

        //=============================================================

        assertTrue(isBlockedAccFrom);
        assertTrue(isBlockedAccTo);
        assertFalse(isBlockedAcc);

    }

    public void testNotNull() {
        assertNotNull(accounts);
        assertNotNull(bank);
        assertNotNull(accounts.get(numbers.get((int) (Math.random() * numbers.size()))));
        assertNotNull(numbers.get((int) (Math.random() * numbers.size())));
        assertNotNull(threads);
    }
}
