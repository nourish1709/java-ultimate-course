package com.nourish1709;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        int[] ints = IntStream.generate(() -> ThreadLocalRandom.current().nextInt())
                .limit(100)
                .toArray();

        System.out.println("Before sorting: ");
        System.out.println(Arrays.toString(ints));

        ForkJoinPool.commonPool().invoke(new MergeSortRecursiveAction(ints));

        System.out.println(Arrays.toString(ints));
    }
}
