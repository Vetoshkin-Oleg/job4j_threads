package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> { }
        );
        Thread second = new Thread(
                () -> { }
        );
        second.start();
        first.start();
        System.out.println(first.getName() + " first");
        System.out.println(second.getName() + " second");
        System.out.println("Нити first и second завершили работу. Работа завершена");
    }
}