package concurrency.chapter7;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * InterruptBorrowedThread
 * <p/>
 * Scheduling an interrupt on a borrowed thread
 *
 * @author Brian Goetz and Tim Peierls
 */
public class TimedRun1 {
    private static final ScheduledExecutorService CANCEL = Executors.newScheduledThreadPool(1);

    public static void timedRun(Runnable r, long timeout, TimeUnit unit) {
        final Thread taskThread = Thread.currentThread();
        CANCEL.schedule(taskThread::interrupt, timeout, unit);
        r.run();
    }
}