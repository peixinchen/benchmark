package com.bittech.javase.benchmark.cases;


import java.util.List;
import java.util.ArrayList;
import java.util.Enumeration;
import java.net.URL;
import java.io.IOException;
import java.io.File;


public class BCaseLoader {
    public class BCaseRunner {
        List<BenchmarkCase> bCaseList;

        private BCaseRunner(List<BenchmarkCase> bCaseList) {
            this.bCaseList = bCaseList;
        }

        public void run() {
            for (BenchmarkCase bCase : bCaseList) {
                runCase(bCase);
            }
        }

        private void runCase(BenchmarkCase bCase) {
            System.out.println("开始用例: " + bCase.name());
            System.out.println("描述: " + bCase.preDesc());
            System.out.println("执行准备工作 ...");
            bCase.init();

            System.out.println("开始测试");
            long s = System.nanoTime();
            bCase.execute();
            long e = System.nanoTime();
            double ms = (e - s) * 1.0 / 1000_000;
            System.out.printf("结束测试，共耗时 %.4f 毫秒%n", ms);

            System.out.println("总结: " + bCase.postDesc());
            System.out.println("===============================");
        }
    }

    public BCaseRunner load() {
        List<String> bCaseClassNameList = scanBCaseClassNameList();
        List<BenchmarkCase> bCaseList = buildBCaseList(bCaseClassNameList);

        System.out.printf("共加载 %d 个有效基准测试 case。%n%n", bCaseList.size());

        return new BCaseRunner(bCaseList);
    }

    private List<String> scanBCaseClassNameList() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        List<String> classNameList = new ArrayList<>();
        try {
            Enumeration<URL> urls = classLoader.getResources("com/bittech/javase/benchmark/cases/impl");
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (!url.getProtocol().equals("file")) {
                    continue;
                }

                classNameList.addAll(scanClassesFromFile(url.getPath())); 
            }
        } catch (IOException ignored) {
            return classNameList;
        }

        return classNameList;
    }

    private List<String> scanClassesFromFile(String path) {
        File dir = new File(path);
        List<String> classNameList = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files == null) {
            return classNameList;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                continue;
            }

            String filename = file.getName();

            if (!filename.endsWith(".class")) {
                continue;
            }

            if (filename.lastIndexOf("$") != -1) {
                continue;
            }

            // 去掉 .class
            String name = filename.substring(0, filename.length() - 6);

            classNameList.add("com.bittech.javase.benchmark.cases.impl." + name);
        }

        return classNameList;
    }

    private List<BenchmarkCase> buildBCaseList(List<String> bCaseClassNameList) {
        List<BenchmarkCase> bCaseList = new ArrayList<>(bCaseClassNameList.size());

        for (String className : bCaseClassNameList) {
            try {
                Class<?> bCaseClass = Class.forName(className);
                if (!isBCaseClass(bCaseClass)) {
                    continue;
                }

                BenchmarkCase bCase = (BenchmarkCase)bCaseClass.newInstance();
                bCaseList.add(bCase);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ignored) {}
        }

        return bCaseList;
    }

    private boolean isBCaseClass(Class<?> bCaseClass) {
        return BenchmarkCase.class.isAssignableFrom(bCaseClass);
    }
}
