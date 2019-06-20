package com.bittech.javase.benchmark.util;

import java.util.Arrays;

public class SortMethodImpl {
    public static SortMethod getQuickSort() {
        return new SortMethod() {
            @Override
            public String name() {
                return "快速排序";
            }

            @Override
            public void sort(int[] a) {
                SortUtil.quickSort(a);
            }
        };
    }

    public static SortMethod getMergeSort() {
        return new SortMethod() {
            @Override
            public String name() {
                return "归并排序";
            }

            @Override
            public void sort(int[] a) {
                SortUtil.mergeSort(a);
            }
        };
    }

    public static SortMethod getParallelSort() {
        return new SortMethod() {
            @Override
            public String name() {
                return "并行排序";
            }

            @Override
            public void sort(int[] a) {
                SortUtil.parallelSort(a);
            }
        };
    }

    public static SortMethod getArraysSort() {
        return new SortMethod() {
            @Override
            public String name() {
                return "Arrays 的排序";
            }

            @Override
            public void sort(int[] a) {
                Arrays.sort(a);
            }
        };
    }

    public static SortMethod getArraysParallelSort() {
        return new SortMethod() {
            @Override
            public String name() {
                return "Arrays 的并行排序";
            }

            @Override
            public void sort(int[] a) {
                Arrays.parallelSort(a);
            }
        };
    }
}
