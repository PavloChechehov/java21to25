package com.paul;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class VirtualThreadDemo {
    public static void main(String[] args) throws InterruptedException {

/*        Thread.Builder vThreadBuilder = Thread.ofVirtual().name("virtual-thread-", 0);


        Thread nThread = new Thread(() -> System.out.println("This is a normal thread running"));
        nThread.join();
        Thread vThread = vThreadBuilder.start(() -> System.out.println("This is a virtual thread running..."));

        Thread vThread2 = vThreadBuilder.factory().newThread(() -> System.out.println("This is a virtual2 thread running..."));
        vThread2.join();

        System.out.println(nThread.getName());
        System.out.println(vThread.getName());
        System.out.println(vThread2.getName());*/

        //1.  oracle documentation. Creation virtual thread from the ThreadBuilder
/*        Thread.Builder builder = Thread.ofVirtual().name("worker-", 0);
        Runnable task = () -> {
            System.out.println("Thread ID: " + Thread.currentThread().threadId());
        };

        // name "worker-0"
        Thread t1 = builder.start(task);
        t1.join();
        System.out.println(t1.getName() + " terminated");

        // name "worker-1"
        Thread t2 = builder.start(task);
        t2.join();
        System.out.println(t2.getName() + " terminated");

        Thread t3 = builder.factory().newThread(() -> System.out.println("Thread ID: " + Thread.currentThread().threadId()));
        t3.join();
        System.out.println(t3.getName() + " terminated");*/

//      2. Creation thread from the Executors
        try(ExecutorService myExecutor = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<?> future = myExecutor.submit(() -> System.out.println("Running new virtual thread"));
            future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Executor is closing...");
        }

    }/**/


}
