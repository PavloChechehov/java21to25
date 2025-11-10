package com.paul.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SharedCounter {
    private int count = 0;
    private final SpinLock spinLock = new SpinLock();

    public void increment() {
        spinLock.lock();
        try {
            count++;
        } finally {
            spinLock.unlock();
        }
    }

    public int getCount() {
        return count;
    }

    public static void main(String[] args) {
        SharedCounter sharedCounter = new SharedCounter();

        try (ExecutorService executorService = Executors.newFixedThreadPool(5)) {

            //test the sharedCounter code
            for (int i = 0; i < 5; i++) {
                executorService.submit(sharedCounter::increment);
                System.out.println(sharedCounter.getCount());
            }


        }


    }
}
