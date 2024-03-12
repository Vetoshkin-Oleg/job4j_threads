package ru.job4j.pools;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RowColSum {

    public static Sums[] sum(int[][] matrix) {
        Sums[] result = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int sumRow = 0;
            int sumCol = 0;
            for (int j = 0; j < matrix.length; j++) {
                sumRow += matrix[i][j];
                sumCol += matrix[j][i];
            }
            result[i] = new Sums();
            result[i].setRowSum(sumRow);
            result[i].setColSum(sumCol);
        }
        return result;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] result = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            result[i] = new Sums();
            result[i].setRowSum(sumOfRow(matrix, i).get());
            result[i].setColSum(sumOfCol(matrix, i).get());
        }
        return result;
    }

    private static CompletableFuture<Integer> sumOfRow(int[][] matrix, int numberOfRow) {
        return CompletableFuture.supplyAsync(() -> {
            int sumRow = 0;
            for (int j = 0; j < matrix.length; j++) {
                sumRow += matrix[numberOfRow][j];
            }
            return sumRow;
        });
    }

    private static CompletableFuture<Integer> sumOfCol(int[][] matrix, int numberOfCol) {
        return CompletableFuture.supplyAsync(() -> {
            int sumCol = 0;
            for (int[] ints : matrix) {
                sumCol += ints[numberOfCol];
            }
            return sumCol;
        });
    }
}