package com.bittech.javase.benchmark.cases.impl;


import com.bittech.javase.benchmark.cases.AbstractSortBCase;
import com.bittech.javase.benchmark.util.SortMethodImpl;
import com.bittech.javase.benchmark.util.DistributionMethodImpl;


public class SortParallelSortNearlyOrderedDescendBCase extends AbstractSortBCase {
    public SortParallelSortNearlyOrderedDescendBCase() {
        super(SortMethodImpl.getParallelSort(), DistributionMethodImpl.getNearlyOrderedDescend());
    }
}