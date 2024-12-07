package com.abr_arn_project;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class PerformanceTest {
    public static void main(String[] args) {
        // Create a file to export the results
        try (PrintWriter writer = new PrintWriter("performance_results.txt")) {
            // Execute tests with different sizes of data
            runTest(1_000_000, writer);
            runTest(10_000_000, writer);
            runTest(100_000_000, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void runTest(int size, PrintWriter writer) {
        // Display and write the test size
        System.out.println("Running test with " + size + " elements");
        writer.println("Running test with " + size + " elements");
        writer.flush();  // Ensure the data is written to the file immediately

        // Initialize ARN and ABR trees
        ARN<Integer> arn = new ARN<>();
        ABR<Integer> abr = new ABR<>();

        // Generate random values
        ArrayList<Integer> randomValues = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            randomValues.add(rand.nextInt(100));
        }

        // Average case: Insertion of values in random order
        Collections.shuffle(randomValues);
        long startTime = System.nanoTime();
        for (Integer value : randomValues) {
            arn.add(value);
        }
        long endTime = System.nanoTime();
        double arnRandomInsertTime = (endTime - startTime) / 1_000_000_000.0;
        System.out.println("Insertion time for ARN (random order): " + arnRandomInsertTime + " s");
        writer.println("Insertion time for ARN (random order): " + arnRandomInsertTime + " s");
        writer.flush();  // Ensure the data is written to the file immediately

        startTime = System.nanoTime();
        for (Integer value : randomValues) {
            abr.add(value);
        }
        endTime = System.nanoTime();
        double abrRandomInsertTime = (endTime - startTime) / 1_000_000_000.0;
        System.out.println("Insertion time for ABR (random order): " + abrRandomInsertTime + " s");
        writer.println("Insertion time for ABR (random order): " + abrRandomInsertTime + " s");
        writer.flush();  // Ensure the data is written to the file immediately

        // Worst case for ABR: Insertion of values in sorted order
        ArrayList<Integer> sortedValues = new ArrayList<>(randomValues);
        Collections.sort(sortedValues);

        // Reset the trees before the next test
        arn.clear();
        abr.clear();

        // Insertion in sorted order for ARN
        startTime = System.nanoTime();
        for (Integer value : sortedValues) {
            arn.add(value);
        }
        endTime = System.nanoTime();
        double arnSortedInsertTime = (endTime - startTime) / 1_000_000_000.0;
        System.out.println("Insertion time for ARN (sorted order): " + arnSortedInsertTime + " s");
        writer.println("Insertion time for ARN (sorted order): " + arnSortedInsertTime + " s");
        writer.flush();  // Ensure the data is written to the file immediately

        // Insertion in sorted order for ABR
        startTime = System.nanoTime();
        for (Integer value : sortedValues) {
            abr.add(value);
        }
        endTime = System.nanoTime();
        double abrSortedInsertTime = (endTime - startTime) / 1_000_000_000.0;
        System.out.println("Insertion time for ABR (sorted order): " + abrSortedInsertTime + " s");
        writer.println("Insertion time for ABR (sorted order): " + abrSortedInsertTime + " s");
        writer.flush();  // Ensure the data is written to the file immediately

        // Measure the search time for keys 0,...,2n-1
        int n = size;
        long arnSearchTime = 0;
        long abrSearchTime = 0;

        // Searching keys in ARN
        startTime = System.nanoTime();
        for (int i = 0; i < 2 * n; i++) {
            arn.contains(i);
        }
        endTime = System.nanoTime();
        arnSearchTime = endTime - startTime;
        System.out.println("Search time for ARN: " + arnSearchTime / 1_000_000_000.0 + " s");
        writer.println("Search time for ARN: " + arnSearchTime / 1_000_000_000.0 + " s");
        writer.flush();  // Ensure the data is written to the file immediately

        // Searching keys in ABR
        startTime = System.nanoTime();
        for (int i = 0; i < 2 * n; i++) {
            abr.contains(i);
        }
        endTime = System.nanoTime();
        abrSearchTime = endTime - startTime;
        System.out.println("Search time for ABR: " + abrSearchTime / 1_000_000_000.0 + " s");
        writer.println("Search time for ABR: " + abrSearchTime / 1_000_000_000.0 + " s");
        writer.flush();  // Ensure the data is written to the file immediately

        // Empty line to separate results of different tests
        System.out.println();
        writer.println();
        writer.flush();  // Ensure the data is written to the file immediately
    }
}
