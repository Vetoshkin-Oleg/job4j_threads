package concurrency.chapter16;

import net.jcip.annotations.ThreadSafe;

/**
 * EagerInitialization
 * <p/>
 * Eager initialization
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class EagerInitialization {
    private static final Resource RESOURCE = new Resource();

    public static Resource getResource() {
        return RESOURCE;
    }

    static class Resource {
    }
}