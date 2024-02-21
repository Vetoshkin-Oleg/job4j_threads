package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        int v;
        do {
            v = get();
        } while (count.compareAndSet(v, v + 1));
    }

    public int get() {
        return count.get();
    }
}