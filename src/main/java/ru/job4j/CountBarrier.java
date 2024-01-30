package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class CountBarrier {
    @GuardedBy("monitor")
    private final Object monitor = this;

    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            System.out.println("method count()");
            count++;
            System.out.println(count);
            monitor.notifyAll();
        }
    }

    public void await() {
        synchronized (monitor) {
            System.out.println("method await()");
            while (count < total) {
                System.out.println("count < total");
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    System.out.println("Thread.currentThread().interrupt()");
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}