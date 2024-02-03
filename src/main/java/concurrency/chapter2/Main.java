package concurrency.chapter2;

public class Main {
    public static void main(String[] args) {
        Thread one = new Thread(() -> {
            Widget widget = new Widget();
            widget.doSomething();
        }, "One");

        Thread two = new Thread(() -> {
            LoggingWidget loggingWidget = new LoggingWidget();
            loggingWidget.doSomething();
        }, "Two");

        one.start();
        two.start();
    }
}