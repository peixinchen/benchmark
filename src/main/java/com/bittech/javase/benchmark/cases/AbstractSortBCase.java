package com.bittech.javase.benchmark.cases;


import com.bittech.javase.benchmark.util.SortMethod;
import com.bittech.javase.benchmark.util.DistributionMethod;


public abstract class AbstractSortBCase implements BenchmarkCase {
    private SortMethod sortMethod;
    private DistributionMethod distributionMethod;
    private int length;
    private int[] a;

    protected AbstractSortBCase(SortMethod sortMethod, DistributionMethod distributionMethod) {
        this.sortMethod = sortMethod;
        this.distributionMethod = distributionMethod;
        this.length = 2000_0000;
    }

    @Override
    public String name() {
        return String.format("%s（%s）", sortMethod.name(), distributionMethod.name());
    }

    @Override
    public String preDesc() {
        return String.format("对 %d 个数据进行排序", length);
    }

    @Override
    public String postDesc() {
        return String.format("排序结束");
    }

    @Override
    public void init() {
        a = distributionMethod.build(length);
    }

    @Override
    public void execute() {
        sortMethod.sort(a);
    }

    @Override
    public void finish() {
        a = null;
    }
}
