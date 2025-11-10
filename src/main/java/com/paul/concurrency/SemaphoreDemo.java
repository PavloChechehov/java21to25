package com.paul.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreDemo {
    private final Semaphore semaphore = new Semaphore(3); // 3 permits

    public void accessResource() {
        try {
            semaphore.acquire(); // Decrease permit or wait if none
            System.out.println("==========================");
            System.out.println(Thread.currentThread().getName() + " acquired access");
            Thread.sleep(500); // Simulate work
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            semaphore.release(); // Increase permit
            System.out.println(Thread.currentThread().getName() + " released access");
            System.out.println("+++++++++++++++++++++++++++");
        }
    }


    public static void main(String[] args) {

        SemaphoreDemo semaphoreDemo = new SemaphoreDemo();

        ExecutorService executorService = Executors.newFixedThreadPool(6);
        for (int i = 0; i < 6; i++) {
            executorService.submit(semaphoreDemo::accessResource);
        }
        executorService.close();
    }
}


