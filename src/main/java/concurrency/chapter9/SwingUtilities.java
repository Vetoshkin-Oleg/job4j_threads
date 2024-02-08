package concurrency.chapter9;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.*;

/**
 * SwingUtilities
 * <p/>
 * Implementing SwingUtilities using an Executor
 *
 * @author Brian Goetz and Tim Peierls
 */
public class SwingUtilities {
    private static final ExecutorService EXEC =
            Executors.newSingleThreadExecutor(new SwingThreadFactory());
    private static volatile Thread swingThread;

    private static class SwingThreadFactory implements ThreadFactory {
        public Thread newThread(Runnable r) {
            swingThread = new Thread(r);
            return swingThread;
        }
    }

    public static boolean isEventDispatchThread() {
        return Thread.currentThread() == swingThread;
    }

    public static void invokeLater(Runnable task) {
        EXEC.execute(task);
    }

    public static void invokeAndWait(Runnable task) throws InterruptedException, InvocationTargetException {
        Future f = EXEC.submit(task);
        try {
            f.get();
        } catch (ExecutionException e) {
            throw new InvocationTargetException(e);
        }
    }
}