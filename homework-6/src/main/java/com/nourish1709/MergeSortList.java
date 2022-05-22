package com.nourish1709;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MergeSortList {

    public static void main(String[] args) {
        var integers = new ArrayList<Integer>();

        for (int i = 0; i < 1_000_000; i++) {
            integers.add(ThreadLocalRandom.current().nextInt());
        }

        mergeSort(integers);

        integers.forEach(integer -> System.out.print(integer + " "));

        for (int i = 0; i < integers.size() - 1; i++) {
            if (integers.get(i) > integers.get(i + 1)) {
                throw new RuntimeException("Array isn't sorted actually");
            }
        }
    }

    public static <T extends Comparable<? super T>> void mergeSort(List<T> list) {
        if (list.size() > 1) {
            var median = list.size() / 2;

            mergeSort(list.subList(0, median));
            mergeSort(list.subList(median, list.size()));

            merge(list);
        }
    }

    private static <T extends Comparable<? super T>> void merge(List<T> list) {
        List<T> temporaryList = new ArrayList<>(list);

        var middle = list.size() / 2;
        var leftLowerIndex = 0;
        var rightLowerIndex = middle;
        var mergedListIndex = 0;

        while (leftLowerIndex < middle && rightLowerIndex < list.size()) {
            if (temporaryList.get(leftLowerIndex)
                    .compareTo(temporaryList.get(rightLowerIndex)) <= 0) {
                list.set(mergedListIndex++, temporaryList.get(leftLowerIndex++));
            } else {
                list.set(mergedListIndex++, temporaryList.get(rightLowerIndex++));
            }
        }

        while (leftLowerIndex < middle) {
            list.set(mergedListIndex++, temporaryList.get(leftLowerIndex++));
        }

        while (rightLowerIndex < list.size()) {
            list.set(mergedListIndex++, temporaryList.get(rightLowerIndex++));
        }
    }
}
