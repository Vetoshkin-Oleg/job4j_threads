package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> {
                    for (int i = 0; i < 1_000; i++) {
                        System.out.println(Thread.currentThread().getState() + " first_" + i);
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    for (int i = 0; i < 1_000; i++) {
                        System.out.println(Thread.currentThread().getState() + " second_" + i);
                    }
                }
        );
        second.start();
        first.start();
        while (first.getState() != Thread.State.TERMINATED
                || second.getState() != Thread.State.TERMINATED) {
            System.out.println(Thread.currentThread().getName() + "_currentThread().getName()");
        }
        System.out.println("Нити first и second завершили работу. Работа завершена");
    }
}