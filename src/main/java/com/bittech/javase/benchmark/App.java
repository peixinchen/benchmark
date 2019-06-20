package com.bittech.javase.benchmark;


import com.bittech.javase.benchmark.cases.BCaseLoader;


public class App {
    public static void main(String[] args) {

        new BCaseLoader().load().run();
    }
}
