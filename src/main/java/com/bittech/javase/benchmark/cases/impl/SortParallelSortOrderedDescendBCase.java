package com.bittech.javase.benchmark.cases.impl;


import com.bittech.javase.benchmark.cases.AbstractSortBCase;
import com.bittech.javase.benchmark.util.SortMethodImpl;
import com.bittech.javase.benchmark.util.DistributionMethodImpl;


public class SortParallelSortOrderedDescendBCase extends AbstractSortBCase {
    public SortParallelSortOrderedDescendBCase() {
        super(SortMethodImpl.getParallelSort(), DistributionMethodImpl.getOrderedDescend());
    }
}