package concurrency.chapter4;

import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ListHelder
 * <p/>
 * Examples of thread-safe implementation of
 * put-if-absent helper methods for List
 *
 * @author Brian Goetz and Tim Peierls
 */

@ThreadSafe
class GoodListHelper<E> {
    public List<E> list = Collections.synchronizedList(new ArrayList<>());

    public boolean putIfAbsent(E x) {
        synchronized (list) {
            boolean absent = !list.contains(x);
            if (absent) {
                list.add(x);
            }
            return absent;
        }
    }
}