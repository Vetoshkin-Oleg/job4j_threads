package concurrency.chapter2;

public class Widget {
    public synchronized void doSomething() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Widget doSomething " + i + " " + Thread.currentThread().getName());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("Widget catch");
                Thread.currentThread().interrupt();
            }
        }
    }
}