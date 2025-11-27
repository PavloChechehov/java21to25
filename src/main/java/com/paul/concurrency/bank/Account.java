package com.paul.concurrency.bank;

public class Account {

    private int balance = 10000;

    public void deposit(int amount) {
        balance += amount;
    }

    public void withdraw(int amount) {
//        if (amount > balance) {
//            throw new RuntimeException("Illegal amount exception. Amount - " + amount + " is bigger than balance - " + balance);
//        }
        balance -= amount;
    }

    public int getBalance() {
        return balance;
    }


    public static void transfer(Account acc1, Account acc2, int amount) {
        acc1.withdraw(amount);
        acc2.deposit(amount);
    }
}
