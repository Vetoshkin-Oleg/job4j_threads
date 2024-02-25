package ru.job4j.pool;

import ru.job4j.buffer.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks;
    private final int size;

    public ThreadPool() {
        tasks = new SimpleBlockingQueue<>(10);
        size = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(() -> {
                try {
                    tasks.poll();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, Thread.currentThread().getName() + "_" + i));
        }
    }

    private void initialize() {
        for (Thread runnable : threads) {
            runnable.start();
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        for (Thread runnable : threads) {
            runnable.interrupt();
        }
    }
}