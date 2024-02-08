package concurrency.chapter8;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * MyAppThread
 * <p/>
 * Custom thread base class
 *
 * @author Brian Goetz and Tim Peierls
 */
public class MyAppThread extends Thread {
    public static final String DEFAULT_NAME = "MyAppThread";
    private static volatile boolean debugLifecycle = false;
    private static final AtomicInteger CREATED = new AtomicInteger();
    private static final AtomicInteger ALIVE = new AtomicInteger();
    private static final Logger LOG = Logger.getAnonymousLogger();

    public MyAppThread(Runnable r) {
        this(r, DEFAULT_NAME);
    }

    public MyAppThread(Runnable runnable, String name) {
        super(runnable, name + "-" + CREATED.incrementAndGet());
        setUncaughtExceptionHandler((t, e) -> LOG.log(Level.SEVERE,
                "UNCAUGHT in thread " + t.getName(), e));
    }

    public void run() {
        // Copy debug flag to ensure consistent value throughout.
        boolean debug = debugLifecycle;
        if (debug) {
            LOG.log(Level.FINE, "Created " + getName());
        }
        try {
            ALIVE.incrementAndGet();
            super.run();
        } finally {
            ALIVE.decrementAndGet();
            if (debug) {
                LOG.log(Level.FINE, "Exiting " + getName());
            }
        }
    }

    public static int getThreadsCreated() {
        return CREATED.get();
    }

    public static int getThreadsAlive() {
        return CREATED.get();
    }

    public static boolean getDebug() {
        return debugLifecycle;
    }

    public static void setDebug(boolean b) {
        debugLifecycle = b;
    }
}