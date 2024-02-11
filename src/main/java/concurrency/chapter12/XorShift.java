package concurrency.chapter12;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * XorShift
 *
 * @author Brian Goetz and Tim Peierls
 */
public class XorShift {
    static final AtomicInteger SEQ = new AtomicInteger(8862213);
    int x = -1831433054;

    public XorShift(int seed) {
        x = seed;
    }

    public XorShift() {
        this((int) System.nanoTime() + SEQ.getAndAdd(129));
    }

    public int next() {
        x ^= x << 6;
        x ^= x >>> 21;
        x ^= (x << 7);
        return x;
    }
}