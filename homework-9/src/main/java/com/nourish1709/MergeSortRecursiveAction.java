package com.nourish1709;

import java.util.concurrent.RecursiveAction;

public class MergeSortRecursiveAction extends RecursiveAction {

    private final int[] array;
    private final int start;
    private final int end;

    public MergeSortRecursiveAction(int[] array) {
        this(array, 0, array.length);
    }

    public MergeSortRecursiveAction(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        int length = end - start;
        if (length > 1) {

            var middle = start + (length / 2);

//        fork in two sub arrays
            MergeSortRecursiveAction left = new MergeSortRecursiveAction(array, start, middle + 1);
            MergeSortRecursiveAction right = new MergeSortRecursiveAction(array, middle, end);

            left.fork();
            right.compute();
            left.join();

            mergeSubArrays();
        }
    }

    private void mergeSubArrays() {
        int length = end - start;
        var middle = start + (length / 2);

        int[] leftCopy = new int[middle - start];
        int[] rightCopy = new int[end - middle];

        System.arraycopy(array, start, leftCopy, 0, leftCopy.length);
        System.arraycopy(array, start + leftCopy.length, rightCopy, 0, rightCopy.length);

        int i = 0;
        int j = 0;
        int k = start;

        while (i < leftCopy.length && j < rightCopy.length) {
            if (leftCopy[i] <= rightCopy[j]) {
                array[k++] = leftCopy[i++];
            } else {
                array[k++] = rightCopy[j++];
            }
        }

        while (i < leftCopy.length) {
            array[k++] = leftCopy[i++];
        }

        while (j < rightCopy.length) {
            array[k++] = rightCopy[j++];
        }
    }
}
