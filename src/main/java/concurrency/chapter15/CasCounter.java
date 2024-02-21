package concurrency.chapter15;

import net.jcip.annotations.ThreadSafe;

/**
 * CasCounter
 * <p/>
 * Nonblocking counter using CAS
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class CasCounter {
    private final SimulatedCAS value = new SimulatedCAS();

    public int getValue() {
        return value.get();
    }

    public int increment() {
        int v;
        do {
            v = getValue();
        } while (v != value.compareAndSwap(v, v + 1));
        return v + 1;
    }

    public static void main(String[] args) {
        CasCounter casCounter = new CasCounter();
        System.out.println("main " + casCounter.increment());
    }
}