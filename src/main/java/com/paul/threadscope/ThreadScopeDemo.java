package com.paul.threadscope;

import java.util.Random;
import java.util.Timer;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Joiner;
import java.util.concurrent.TimeUnit;

public class ThreadScopeDemo {
    private static final Random rand = new Random();

    public static void main(String[] args) {

        /*
            Dependence of the Joiner you can check the result of both calculation or when fist occurs the show/return the result
         */
//        try (var scope = StructuredTaskScope.open(Joiner.anySuccessfulResultOrThrow())) {
        try (var scope = StructuredTaskScope.open()) {

            var task1 = scope.fork(() -> calculate(rand.nextInt(100)));
            var task2 = scope.fork(() -> calculate(rand.nextInt(100)));

            scope.join();
//            System.out.println("res = " + res);

            Integer res1 = task1.get();
            Integer res2 = task2.get();

            System.out.println("res1 = " + res1);
            System.out.println("res2 = " + res2);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static Integer calculate(int n) throws InterruptedException {
        int count = 0;
        for (int i = 0; i < n; i++) {
            count++;
            Thread.sleep(10);
        }
        return count;
    }
}
