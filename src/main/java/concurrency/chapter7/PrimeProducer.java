package concurrency.chapter7;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

/**
 * PrimeProducer
 * <p/>
 * Using interruption for cancellation
 *
 * @author Brian Goetz and Tim Peierls
 */
public class PrimeProducer extends Thread {
    private final BlockingQueue<BigInteger> queue;

    PrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            BigInteger p = BigInteger.ONE;
            while (!Thread.currentThread().isInterrupted()) {
                queue.put(p);
                p = p.nextProbablePrime();
            }
        } catch (InterruptedException consumed) {
            System.out.println("Allow thread to exit");
        }
    }

    public void cancel() {
        interrupt();
    }
}