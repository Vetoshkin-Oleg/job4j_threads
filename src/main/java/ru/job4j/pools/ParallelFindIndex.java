package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelFindIndex<T> extends RecursiveTask<Integer> {

    private final T[] collection;
    private final T object;
    private final int from;
    private final int to;

    public ParallelFindIndex(T[] collection, T object, int from, int to) {
        this.collection = collection;
        this.object = object;
        this.from = from;
        this.to = to;
    }

    public static <T> int find(T[] collection, T object) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelFindIndex<>(collection, object, 0, collection.length - 1));
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return linearSearch();
        }
        int middle = (from + to) / 2;
        ParallelFindIndex<T> left = new ParallelFindIndex<>(collection, object, from, middle);
        ParallelFindIndex<T> right = new ParallelFindIndex<>(collection, object, middle + 1, to);
        left.fork();
        right.fork();
        int leftIndex = left.join();
        int rightIndex = right.join();
        return Math.max(leftIndex, rightIndex);
    }

    public int linearSearch() {
        int index = -1;
        for (int i = from; i <= to; i++) {
            if (object.equals(collection[i])) {
                index = i;
                break;
            }
        }
        return index;
    }
}