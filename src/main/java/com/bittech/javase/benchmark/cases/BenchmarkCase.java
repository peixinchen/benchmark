package com.bittech.javase.benchmark.cases;


public interface BenchmarkCase {
    String name();

    String preDesc();

    String postDesc();

    void init();

    void execute();

    void finish();
}
