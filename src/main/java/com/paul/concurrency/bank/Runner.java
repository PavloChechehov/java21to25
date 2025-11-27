package com.paul.concurrency.bank;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Runner {

    private Account acc1 = new Account();
    private Account acc2 = new Account();

    private final ReentrantLock lock1 = new ReentrantLock();
    private final ReentrantLock lock2 = new ReentrantLock();

    private final Random random = new Random();

    public void firstThread() throws InterruptedException {

        for (int i = 0; i < 10000; i++) {

            lock1.lock();
            lock2.lock();

            try {
                Account.transfer(acc1, acc2, random.nextInt(10));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }

    }


    public void secondThread() throws InterruptedException {

        for (int i = 0; i < 10000; i++) {
            lock1.lock();
            lock2.lock();

            try {
                Account.transfer(acc2, acc1, random.nextInt(10));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
            ;
        }


    }

    public void finished() {
        System.out.println("Account 1 balance: " + acc1.getBalance());
        System.out.println("Account 2 balance: " + acc2.getBalance());
        System.out.println("Total balance: " + (acc2.getBalance() + acc1.getBalance()));
    }


    static void main() throws InterruptedException {

        Runner runner = new Runner();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    runner.firstThread();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    runner.secondThread();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        t1.start();
        t2.start();

        t1.join();
        t2.join();


        runner.finished();

    }

}
