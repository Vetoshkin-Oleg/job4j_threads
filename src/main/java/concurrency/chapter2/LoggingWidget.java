package concurrency.chapter2;

public class LoggingWidget extends Widget {
    public synchronized void doSomething() {
        System.out.println("LoggingWidget calling doSomething: " + Thread.currentThread().getName());
        super.doSomething();
    }
}