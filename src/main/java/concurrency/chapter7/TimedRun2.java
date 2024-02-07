package concurrency.chapter7;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static concurrency.chapter5.LaunderThrowable.launderThrowable;
import static java.util.concurrent.Executors.newScheduledThreadPool;

/**
 * TimedRun2
 * <p/>
 * Interrupting a task in a dedicated thread
 *
 * @author Brian Goetz and Tim Peierls
 */
public class TimedRun2 {
    private static final ScheduledExecutorService CANCEL = newScheduledThreadPool(1);

    public static void timedRun(final Runnable r, long timeout, TimeUnit unit) throws InterruptedException {
        class RethrowableTask implements Runnable {
            private volatile Throwable t;

            public void run() {
                try {
                    r.run();
                } catch (Throwable t) {
                    this.t = t;
                }
            }

            void rethrow() {
                if (t != null) {
                    throw launderThrowable(t);
                }
            }
        }

        RethrowableTask task = new RethrowableTask();
        final Thread taskThread = new Thread(task);
        taskThread.start();
        CANCEL.schedule(taskThread::interrupt, timeout, unit);
        taskThread.join(unit.toMillis(timeout));
        task.rethrow();
    }
}