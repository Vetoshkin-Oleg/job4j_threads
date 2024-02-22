package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CASCountTest {

    @Test
    void increment() throws InterruptedException {
        CASCount casCount = new CASCount();
        Thread one = new Thread(
                () -> {
                    for (int i = 0; i < 100_000_000; i++) {
                        casCount.increment();
                    }
                }, "one"
        );
        Thread two = new Thread(
                () -> {
                    for (int i = 0; i < 100_000_000; i++) {
                        casCount.increment();
                    }
                }, "two"
        );
        one.start();
        two.start();
        one.join();
        two.join();
        assertThat(casCount.get()).isEqualTo(200_000_000);
    }

    @Test
    void get() {
        CASCount casCount = new CASCount();
        assertThat(casCount.get()).isEqualTo(0);
    }
}