package concurrency.chapter7;

import java.util.concurrent.*;

import static concurrency.chapter5.LaunderThrowable.launderThrowable;

/**
 * TimedRun
 * <p/>
 * Cancelling a task using Future
 *
 * @author Brian Goetz and Tim Peierls
 */
public class TimedRun {
    private static final ExecutorService EXEC = Executors.newCachedThreadPool();

    public static void timedRun(Runnable r, long timeout, TimeUnit unit) throws InterruptedException {
        Future<?> task = EXEC.submit(r);
        try {
            task.get(timeout, unit);
        } catch (TimeoutException e) {
            System.out.println("task will be cancelled below");
        } catch (ExecutionException e) {
            // exception thrown in task; rethrow
            throw launderThrowable(e.getCause());
        } finally {
            // Harmless if task already completed
            task.cancel(true); // interrupt if running
        }
    }
}