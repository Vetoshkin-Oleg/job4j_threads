package ru.job4j;

public class CountBarrierUse {
    public static void main(String[] args) {
        CountBarrier countBarrier = new CountBarrier(13);
        Thread one = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    countBarrier.count();
                },
                "One"
        );
        Thread two = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    countBarrier.await();
                },
                "Two"
        );
        one.start();
        two.start();
    }
}