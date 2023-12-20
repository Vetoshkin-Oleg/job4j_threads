package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        var process = new char[] {'-', '\\', '|', '/'};
        int index = 0;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                while (true) {
                    System.out.print("\r load: " + process[index]);
                    Thread.sleep(500);
                    index++;
                    if (index == process.length - 1) {
                        index = 0;
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        try {
            Thread.sleep(5000); /* симулируем выполнение параллельной задачи в течение 5 секунд. */
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        progress.interrupt();
    }
}