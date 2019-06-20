package com.bittech.javase.benchmark.util;


import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;


public class SortUtil {
    // 数组长度低于这个阈值，就会使用插入排序
    private static final int INSERT_SORT_THRESHOLD = 47;

    // 数组长度低于这个阈值，就不会再分解为并行任务
    private static final int MIN_ARRAY_SORT_SIZE = 1 << 13;

    /**
     * 原地并行排序
     * @param a 待排序数组
     */
    public static void parallelSort(final int[] a) {
        int parallelism = ForkJoinPool.getCommonPoolParallelism();
        if (a.length <= MIN_ARRAY_SORT_SIZE || parallelism == 1) {
            quickSort(a);
        } else {
            final int[] clone = a.clone();
            class SortAction extends RecursiveAction {
                private int[] src;
                private int[] dest;
                private int left;
                private int right;

                SortAction(int[] src, int[] dest, int left, int right) {
                    this.src = src;
                    this.dest = dest;
                    this.left = left;
                    this.right = right;
                }

                @Override
                protected void compute() {
                    int length = right - left;
                    if (length <= MIN_ARRAY_SORT_SIZE) {
                        quickSortInternal(dest, left, right - 1);
                        return;
                    }

                    int mid = (left + right) >>> 1;
                    SortAction actionLeft = new SortAction(dest, src, left, mid);
                    SortAction actionRight = new SortAction(dest, src, mid, right);
                    invokeAll(actionLeft, actionRight);
                    actionLeft.join();
                    actionRight.join();

                    mergeOrdered(src, dest, left, mid, right);
                }
            }

            new SortAction(clone, a, 0, a.length).invoke();
        }
    }

    /**
     * 原地归并排序
     * @param a 待排序数组
     */
    public static void mergeSort(int[] a) {
        int[] clone = a.clone();
        mergeSortInternal(clone, a, 0, a.length);
    }

    /**
     * 原地快速排序
     * @param a 待排序数组
     */
    public static void quickSort(int[] a) {
        quickSortInternal(a, 0, a.length - 1);
    }

    /**
     * 有间隔地插入排序数组的给定区间 a[left, right]
     *
     * @param a 待排序数组
     * @param left 指示区间的第一个元素，包含
     * @param right 指示区间的最后一个元素，包含
     * @param gap 比较间隔
     */
    private static void insertSortWithGap(int[] a, int left, int right, int gap) {
        for (int i = left + gap; i <= right; i += gap) {
            int t = a[i];
            int j = i - gap;
            for (; j >= left && a[j] > t; j -= gap) {
                a[j + gap] = a[j];
            }
            a[j + gap] = t;
        }
    }

    /**
     * 快速排序数组的给定区间 a[left, right]
     *
     * @param a 待排序数组
     * @param left 指示区间的第一个元素，包含
     * @param right 指示区间的最后一个元素，包含
     */
    private static void quickSortInternal(int[] a, int left, int right) {
        int length = right - left + 1;

        if (length < INSERT_SORT_THRESHOLD) {
            insertSortWithGap(a, left, right, 1);
            return;
        }

        // 五数取中法选定基准值
        // 大约是长度的 1/7
        int seventh = (length >>> 3) + (length >>> 6) + 1;
        int e3 = (left + right) >>> 1;
        int e2 = e3 - seventh;
        int e1 = e2 - seventh;
        int e4 = e3 + seventh;
        int e5 = e4 + seventh;

        insertSortWithGap(a, e1, e5, seventh);

        int[] bounds = partition(a, left, right, e3);
        int less = bounds[0];
        int great = bounds[1];

        quickSortInternal(a, left, less - 1);
        quickSortInternal(a, great + 1, right);
    }

    /**
     * 扫描整个区间，和 a[pivotIndex] 做比较，最终区间被分为三段
     * [left, less)     比基准值小
     * [less, great]    和基准值相等
     * (great, right]   比基准值大
     * @param a 数组
     * @param left 区间左边界（包含）
     * @param right 区间右边界（包含）
     * @param pivotIndex 基准值所在下标
     * @return int[2] { less, great } 要来指定区间的分隔位置
     */
    private static int[] partition(int[] a, int left, int right, int pivotIndex) {
        int pivot = a[pivotIndex];
        int less = left;
        int great = right;

        /*
         * +--------------------------------------+
         * | < pivot | == pivot |   ?   | > pivot |
         * +--------------------------------------+
         * ^         ^          ^       ^         ^
         * left      less       i       great     right
         *
         * Invariants:
         * 1. All in [left, less)       < pivot
         * 2. All in [less, i)          == pivot
         * 3. All in [i, great]        unknown
         * 4. All in (great, right]     > pivot
         */

        for (int i = less; i <= great; i++) {
            if (a[i] == pivot) {
                continue;
            }

            int e = a[i];
            if (e < pivot) {
                a[i] = a[less];
                a[less++] = e;
                continue;
            }

            // e > pivot
            // Skip all elements that > pivot
            while (a[great] > pivot) {
                great--;
            }

            if (a[great] < pivot) {
                a[i] = a[less];
                a[less++] = a[great];
            } else {
                a[i] = pivot;
            }

            a[great--] = e;
        }

        return new int[] { less, great };
    }

    /**
     * 归并排序 src，排序后的结果在 dest 中，区间范围时 [low, high)
     * @param src 待排序数组
     * @param dest 排序后数组
     * @param low 左边界（包含）
     * @param high 右边界（不包含）
     */
    private static void mergeSortInternal(int[] src, int[] dest, int low, int high) {
        int length = high - low;
        if (length < INSERT_SORT_THRESHOLD) {
            insertSortWithGap(dest, low, high - 1, 1);
            return;
        }

        int mid = (low + high) >>> 1;
        mergeSortInternal(dest, src, low, mid);
        mergeSortInternal(dest, src, mid, high);

        mergeOrdered(src, dest, low, mid, high);
    }

    /**
     * 从 src 合并两个独立有序的区间 [low, mid) 和 [mid, high) 到 dest
     * @param src   有序原数组
     * @param dest  合并后的数组
     * @param low   左边区间的左边界（包含）
     * @param mid   左边区间的右边界（不包含）和右边区间的左边界（包含)
     * @param high  右边区间的右边界（不包含）
     */
    private static void mergeOrdered(int[] src, int[] dest, int low, int mid, int high) {
        int length = high - low;

        if (src[mid - 1] <= src[mid]) {
            System.arraycopy(src, low, dest, low, length);
            return;
        }

        int p = low;
        int q = mid;
        for (int i = low; i < high; i++) {
            if (q >= high || p < mid && src[p] <= src[q]) {
                dest[i] = src[p++];
            } else {
                dest[i] = src[q++];
            }
        }
    }
}
