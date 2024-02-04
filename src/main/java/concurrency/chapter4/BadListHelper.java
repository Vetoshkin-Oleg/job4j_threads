package concurrency.chapter4;

import net.jcip.annotations.NotThreadSafe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ListHelder
 * <p/>
 * Examples of non-thread-safe implementation of
 * put-if-absent helper methods for List
 *
 * @author Brian Goetz and Tim Peierls
 */

@NotThreadSafe
class BadListHelper<E> {
    public List<E> list = Collections.synchronizedList(new ArrayList<>());

    public synchronized boolean putIfAbsent(E x) {
        boolean absent = !list.contains(x);
        if (absent) {
            list.add(x);
        }
        return absent;
    }
}