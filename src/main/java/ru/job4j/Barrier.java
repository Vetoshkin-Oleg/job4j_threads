package ru.job4j;

public class Barrier {
    private boolean flag = false;

    private final Object monitor = this;

    public void on() {
        synchronized (monitor) {
            System.out.println("method on()");
            flag = true;
            monitor.notifyAll();
        }
    }

    public void off() {
        synchronized (monitor) {
            System.out.println("method off()");
            flag = false;
            monitor.notifyAll();
        }
    }

    public void check() {
        synchronized (monitor) {
            System.out.println("method check()");
            while (!flag) {
                try {
                    System.out.println(Thread.currentThread().getName() + " wait");
                    monitor.wait();
                } catch (InterruptedException e) {
                    System.out.println("Thread.currentThread().interrupt()");
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}