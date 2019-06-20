package com.bittech.javase.benchmark.cases.impl;


import com.bittech.javase.benchmark.cases.AbstractSortBCase;
import com.bittech.javase.benchmark.util.SortMethodImpl;
import com.bittech.javase.benchmark.util.DistributionMethodImpl;


public class Sort%s%sBCase extends AbstractSortBCase {
    public Sort%s%sBCase() {
        super(SortMethodImpl.get%s(), DistributionMethodImpl.get%s());
    }
}
