package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

class RowColSumTest {

    @Test
    void sumLength7() {
        int[][] matrix =
                {{1, 2, 3, 4, 5, 6, 7},
                {1, 2, 3, 4, 5, 6, 7},
                {1, 2, 3, 4, 5, 6, 7},
                {1, 2, 3, 4, 5, 6, 7},
                {1, 2, 3, 4, 5, 6, 7},
                {1, 2, 3, 4, 5, 6, 7},
                {1, 2, 3, 4, 5, 6, 7}};
        RowColSum.Sums[] sums = RowColSum.sum(matrix);
        int sumOf3 = sums[3].getColSum() + sums[3].getRowSum();
        assertThat(sumOf3).isEqualTo(56);
    }

    @Test
    void sumLength8() {
        int[][] matrix =
                        {{8, 7, 6, 5, 4, 3, 2, 1},
                        {8, 7, 6, 5, 4, 3, 2, 1},
                        {8, 7, 6, 5, 4, 3, 2, 1},
                        {8, 7, 6, 5, 4, 3, 2, 1},
                        {8, 7, 6, 5, 4, 3, 2, 1},
                        {8, 7, 6, 5, 4, 3, 2, 1},
                        {8, 7, 6, 5, 4, 3, 2, 1},
                        {8, 7, 6, 5, 4, 3, 2, 1}};
        RowColSum.Sums[] sums = RowColSum.sum(matrix);
        int sumOf7 = sums[7].getColSum() + sums[7].getRowSum();
        assertThat(sumOf7).isEqualTo(44);
    }

    @Test
    void asyncSumLength7() throws ExecutionException, InterruptedException {
        int[][] matrix =
                        {{1, 2, 3, 4, 5, 6, 7},
                        {1, 2, 3, 4, 5, 6, 7},
                        {1, 2, 3, 4, 5, 6, 7},
                        {1, 2, 3, 4, 5, 6, 7},
                        {1, 2, 3, 4, 5, 6, 7},
                        {1, 2, 3, 4, 5, 6, 7},
                        {1, 2, 3, 4, 5, 6, 7}};
        RowColSum.Sums[] sums = RowColSum.asyncSum(matrix);
        int sumOf3 = sums[3].getColSum() + sums[3].getRowSum();
        assertThat(sumOf3).isEqualTo(56);
    }

    @Test
    void asyncSumLength8() throws ExecutionException, InterruptedException {
        int[][] matrix =
                        {{8, 7, 6, 5, 4, 3, 2, 1},
                        {8, 7, 6, 5, 4, 3, 2, 1},
                        {8, 7, 6, 5, 4, 3, 2, 1},
                        {8, 7, 6, 5, 4, 3, 2, 1},
                        {8, 7, 6, 5, 4, 3, 2, 1},
                        {8, 7, 6, 5, 4, 3, 2, 1},
                        {8, 7, 6, 5, 4, 3, 2, 1},
                        {8, 7, 6, 5, 4, 3, 2, 1}};
        RowColSum.Sums[] sums = RowColSum.asyncSum(matrix);
        int sumOf7 = sums[7].getColSum() + sums[7].getRowSum();
        assertThat(sumOf7).isEqualTo(44);
    }
}