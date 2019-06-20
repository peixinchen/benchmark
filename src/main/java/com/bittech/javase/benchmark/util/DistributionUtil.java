package com.bittech.javase.benchmark.util;


import java.util.Arrays;
import java.util.Random;


public class DistributionUtil {
    private static final Random random = new Random(20190620);

    public static int[] buildRandom(int length) {
        int[] a = new int[length];
        for (int i = 0; i < length; i++) {
            a[i] = random.nextInt(length >>> 1);
        }

        return a;
    }

    public static int[] buildEquivalent(int length) {
        int[] a = new int[length];
        int n = random.nextInt(length / 2);
        Arrays.fill(a, n);
        return a;
    }

    private static void vandalize(int[] a, double percent) {
        int n = (int)(a.length * percent);

        for (int i = 0; i < n; i++) {
            a[random.nextInt(a.length)] = random.nextInt(a.length / 2);
        }
    }

    public static int[] buildNearlyEquivalent(int length, double percent) {
        int[] a = buildEquivalent(length);
        vandalize(a,percent);
        return a;
    }

    public static int[] buildOrderedAscend(int length) {
        int[] a = buildRandom(length);
        Arrays.sort(a);
        return a;
    }

    public static int[] buildNearlyOrderedAscend(int length, double percent) {
        int[] a = buildOrderedAscend(length);
        vandalize(a, percent);
        return a;
    }

    private static void reverse(int[] a) {
        int i = 0;
        int j = a.length - 1;

        while (i < j) {
            int t = a[i];
            a[i] = a[j];
            a[j] = t;
            i++; j--;
        }
    }

    public static int[] buildOrderedDescend(int length) {
        int[] a = buildOrderedAscend(length);
        reverse(a);
        return a;
    }

    public static int[] buildNearlyOrderedDescend(int length, double percent) {
        int[] a = buildOrderedDescend(length);
        vandalize(a, percent);
        return a;
    }
}
