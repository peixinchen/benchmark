package com.bittech.javase.benchmark.util;


public interface DistributionMethod {
    String name();
    int[] build(int length);
}
