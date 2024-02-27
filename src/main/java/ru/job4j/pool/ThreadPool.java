package ru.job4j.pool;

import ru.job4j.buffer.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks;
    private final int size = Runtime.getRuntime().availableProcessors();

    public ThreadPool(int limit) {
        this.tasks = new SimpleBlockingQueue<>(limit);
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(() -> {
                try {
                    tasks.poll().run();
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

    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool(10);
        Runnable job0 = () -> System.out.println("Задача 0");
        Runnable job1 = () -> System.out.println("Задача 1");
        Runnable job2 = () -> System.out.println("Задача 2");
        Runnable job3 = () -> System.out.println("Задача 3");
        Runnable job4 = () -> System.out.println("Задача 4");
        Runnable job5 = () -> System.out.println("Задача 5");
        Runnable job6 = () -> System.out.println("Задача 6");
        Runnable job7 = () -> System.out.println("Задача 7");
        Runnable job8 = () -> System.out.println("Задача 8");
        Runnable job9 = () -> System.out.println("Задача 9");

        threadPool.work(job0);
        threadPool.work(job1);
        threadPool.work(job2);
        threadPool.work(job3);
        threadPool.work(job4);
        threadPool.work(job5);
        threadPool.work(job6);
        threadPool.work(job7);
        threadPool.work(job8);
        threadPool.work(job9);

        threadPool.initialize();
    }
}