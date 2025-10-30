package com.paul.virtualthreads;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class VirtualThreadPerfDemo {

        private static volatile int counter = 0;
//    private static final AtomicInteger counter = new AtomicInteger(0);
//
//    public static void main(String[] args) throws InterruptedException {
//        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
//            for (int i = 0; i < 10_000; i++) {
//                executor.submit(() -> {
//                    counter.incrementAndGet();
//                    System.out.println(Thread.currentThread());
//                });
//            }
//        }
//
//
//        System.out.println(counter);
//    }


//    private static final AtomicInteger counter = new AtomicInteger(0);
    private static final AtomicInteger taskIdGenerator = new AtomicInteger(1);

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        System.out.println();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            CountDownLatch latch = new CountDownLatch(10_000);

            for (int i = 0; i < 1_000_000; i++) {
                executor.submit(() -> {
                    int taskId = taskIdGenerator.getAndIncrement(); // Unique ID for this task
                    counter += 1;
//                    counter.incrementAndGet();
//                    System.out.println("CustomTask[" + taskId + "] running on " + Thread.currentThread());
                    latch.countDown();
                });
            }

            latch.await(); // Wait for all tasks to finish
        }

        long finish = System.currentTimeMillis();
        long finisedTime = (finish - start);
        System.out.println("Time: " + finisedTime);
        System.out.println("Final counter value: " + counter);
        System.out.println("Final task counter value: " + taskIdGenerator.get());
    }
}
