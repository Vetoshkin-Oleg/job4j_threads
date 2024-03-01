package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

import static org.assertj.core.api.Assertions.assertThat;

class ParallelFindIndexTest {

    @Test
    void parallelSearchWhen10ElementsStrings() {
        String[] strings = new String[10];
        strings[0] = "one";
        strings[1] = "two 1";
        strings[2] = "three";
        strings[3] = "one";
        strings[4] = "two";
        strings[5] = "three";
        strings[6] = "one";
        strings[7] = "two";
        strings[8] = "three";
        strings[9] = "four";
        String object = "two";
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ParallelFindIndex<String> parallelFindIndex =
                new ParallelFindIndex<>(strings, object, 0, strings.length);
        assertThat(forkJoinPool.invoke(parallelFindIndex)).isEqualTo(4);
    }

    @Test
    void parallelSearchWhen5ElementsChar() {
        Character[] characters = new Character[5];
        characters[0] = 'a';
        characters[1] = 'b';
        characters[2] = 'c';
        characters[3] = 'd';
        characters[4] = 'd';
        Character object = 'd';
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ParallelFindIndex<Character> parallelFindIndex =
                new ParallelFindIndex<>(characters, object, 0, characters.length);
        assertThat(forkJoinPool.invoke(parallelFindIndex)).isEqualTo(3);
    }

    @Test
    void parallelSearchWhenObjectNotFound() {
        Character[] characters = new Character[5];
        characters[0] = 'a';
        characters[1] = 'b';
        characters[2] = 'c';
        characters[3] = 'd';
        characters[4] = 'd';
        Character object = 'D';
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ParallelFindIndex<Character> parallelFindIndex =
                new ParallelFindIndex<>(characters, object, 0, characters.length);
        assertThat(forkJoinPool.invoke(parallelFindIndex)).isEqualTo(-1);
    }

    @Test
    void parallelSearchWhen15ElementsInteger() {
        Integer[] ints = new Integer[15];
        Arrays.fill(ints, 5);
        ints[7] = 7;
        Integer object = 7;
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ParallelFindIndex<Integer> parallelFindIndex =
                new ParallelFindIndex<>(ints, object, 0, ints.length);
        assertThat(forkJoinPool.invoke(parallelFindIndex)).isEqualTo(7);
    }

    @Test
    void parallelSearchWhen20ElementsIntegerVer1() {
        Integer[] ints = new Integer[20];
        Arrays.fill(ints, 5);
        ints[10] = 7;
        Integer object = 7;
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ParallelFindIndex<Integer> parallelFindIndex =
                new ParallelFindIndex<>(ints, object, 0, ints.length);
        assertThat(forkJoinPool.invoke(parallelFindIndex)).isEqualTo(10);
    }

    @Test
    void parallelSearchWhen20ElementsIntegerVer2() {
        Integer[] ints = new Integer[20];
        Arrays.fill(ints, 5);
        ints[19] = 7;
        Integer object = 7;
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ParallelFindIndex<Integer> parallelFindIndex =
                new ParallelFindIndex<>(ints, object, 0, ints.length);
        assertThat(forkJoinPool.invoke(parallelFindIndex)).isEqualTo(19);
    }

    @Test
    void parallelSearchWhen21ElementsIntegerVer2() {
        Integer[] ints = new Integer[21];
        Arrays.fill(ints, 5);
        ints[20] = 7;
        Integer object = 7;
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ParallelFindIndex<Integer> parallelFindIndex =
                new ParallelFindIndex<>(ints, object, 0, ints.length);
        assertThat(forkJoinPool.invoke(parallelFindIndex)).isEqualTo(20);
    }
}