package ru.job4j.pools;

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

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return linearSearch(collection, object);
        }
        int middle = collection.length / 2;
        ParallelFindIndex<T> left = new ParallelFindIndex<>(collection, object, from, middle);
        ParallelFindIndex<T> right = new ParallelFindIndex<>(collection, object, middle + 1, to);
        left.fork();
        right.fork();
        int leftIndex = left.join();
        int rightIndex = right.join();
        return Math.max(leftIndex, rightIndex);
    }

    public int linearSearch(T[] collection, T object) {
        int index = -1;
        for (int i = 0; i < collection.length; i++) {
            if (collection[i].equals(object)) {
                index = i;
                break;
            }
        }
        return index;
    }
}