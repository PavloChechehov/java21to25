package com.paul.concurrency;

import java.util.concurrent.atomic.AtomicBoolean;

public class SpinLock {


    private final AtomicBoolean lock = new AtomicBoolean(false);

    public void lock() {
        while (!lock.compareAndSet(false, true)) {
            System.out.println(this + " acquired access");
            // busy-wait / spin
        }
    }

    public void unlock() {
        lock.set(false);
    }
}
