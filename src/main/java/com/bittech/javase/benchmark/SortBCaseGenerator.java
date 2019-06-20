package com.bittech.javase.benchmark;


import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;


public class SortBCaseGenerator {
    private static String getTemplate(String name) throws IOException {
        String template = null;
        String filename = "templates/" + name + ".tpl";
        ClassLoader classLoader = SortBCaseGenerator.class.getClassLoader();
        try (InputStream in = SortBCaseGenerator.class.getClassLoader().getResourceAsStream(filename)) {
            if (in == null) {
                throw new IOException();
            }

            try (InputStreamReader isr = new InputStreamReader(in)) {
                try (BufferedReader reader = new BufferedReader(isr)) {
                    template = reader.lines().reduce((a, b) -> String.format("%s%n%s", a, b)).orElseThrow(() -> new IOException());
                }
            }
        }

        return template;
    } 

    private static String renderTemplate(String template, String sort, String dis) {
        return String.format(template, sort, dis, sort, dis, sort, dis);
    }

    public static void main(String[] args) throws IOException {
        String[] sortMethods = { "QuickSort", "MergeSort", "ParallelSort", "ArraysSort", "ArraysParallelSort" };
        String[] distributionMethods = {
            "Random", "Equivalent", "NearlyEquivalent",
            "OrderedAscend", "NearlyOrderedAscend",
            "OrderedDescend", "NearlyOrderedDescend"
        };

        String template = getTemplate("SortBCase");

        for (String sort : sortMethods) {
            for (String dis : distributionMethods) {
                String content = renderTemplate(template, sort, dis);
                String filename = String.format("out" + File.separator + "Sort%s%sBCase.java", sort, dis);

                Path file = Paths.get(filename);
                Files.write(file, content.getBytes());
            }
        }
    }
}
