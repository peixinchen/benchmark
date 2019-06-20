package com.bittech.javase.benchmark.cases.impl;


import com.bittech.javase.benchmark.cases.AbstractSortBCase;
import com.bittech.javase.benchmark.util.SortMethodImpl;
import com.bittech.javase.benchmark.util.DistributionMethodImpl;


public class SortQuickSortNearlyEquivalentBCase extends AbstractSortBCase {
    public SortQuickSortNearlyEquivalentBCase() {
        super(SortMethodImpl.getQuickSort(), DistributionMethodImpl.getNearlyEquivalent());
    }
}