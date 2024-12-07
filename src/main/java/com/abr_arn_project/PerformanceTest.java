package com.abr_arn_project;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class PerformanceTest {
    public static void main(String[] args) {
        // Creer un fichier pour exporter les resultats
        try (PrintWriter writer = new PrintWriter("performance_results.txt")) {
            // Executer les tests avec differentes tailles de donnees
            runTest(1_000_000, writer);
            runTest(10_000_000, writer);
            runTest(100_000_000, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void runTest(int size, PrintWriter writer) {
        // Afficher et ecrire la taille du test
        System.out.println("Execution du test avec " + size + " elements");
        writer.println("Execution du test avec " + size + " elements");
        writer.flush();  // S'assurer que les donnees sont ecrites dans le fichier immediatement

        // Initialiser les arbres ARN et ABR
        ARN<Integer> arn = new ARN<>();
        ABR<Integer> abr = new ABR<>();

        // Generer des valeurs aleatoires
        ArrayList<Integer> randomValues = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            randomValues.add(rand.nextInt(100));
        }

        // Cas moyen : Insertion des valeurs dans un ordre aleatoire
        Collections.shuffle(randomValues);
        long startTime = System.nanoTime();
        for (Integer value : randomValues) {
            arn.add(value);
        }
        long endTime = System.nanoTime();
        double arnRandomInsertTime = (endTime - startTime) / 1_000_000_000.0;
        System.out.println("Temps d'insertion pour ARN (ordre aleatoire): " + arnRandomInsertTime + " s");
        writer.println("Temps d'insertion pour ARN (ordre aleatoire): " + arnRandomInsertTime + " s");
        writer.flush();  // S'assurer que les donnees sont ecrites dans le fichier immediatement

        startTime = System.nanoTime();
        for (Integer value : randomValues) {
            abr.add(value);
        }
        endTime = System.nanoTime();
        double abrRandomInsertTime = (endTime - startTime) / 1_000_000_000.0;
        System.out.println("Temps d'insertion pour ABR (ordre aleatoire): " + abrRandomInsertTime + " s");
        writer.println("Temps d'insertion pour ABR (ordre aleatoire): " + abrRandomInsertTime + " s");
        writer.flush();  // S'assurer que les donnees sont ecrites dans le fichier immediatement

        // Cas pire pour ABR : Insertion des valeurs dans un ordre trie
        ArrayList<Integer> sortedValues = new ArrayList<>(randomValues);
        Collections.sort(sortedValues);

        // Reinitialiser les arbres avant le prochain test
        arn.clear();
        abr.clear();

        // Insertion dans l'ordre trie pour ARN
        startTime = System.nanoTime();
        for (Integer value : sortedValues) {
            arn.add(value);
        }
        endTime = System.nanoTime();
        double arnSortedInsertTime = (endTime - startTime) / 1_000_000_000.0;
        System.out.println("Temps d'insertion pour ARN (ordre trie): " + arnSortedInsertTime + " s");
        writer.println("Temps d'insertion pour ARN (ordre trie): " + arnSortedInsertTime + " s");
        writer.flush();  // S'assurer que les donnees sont ecrites dans le fichier immediatement

        // Insertion dans l'ordre trie pour ABR
        startTime = System.nanoTime();
        for (Integer value : sortedValues) {
            abr.add(value);
        }
        endTime = System.nanoTime();
        double abrSortedInsertTime = (endTime - startTime) / 1_000_000_000.0;
        System.out.println("Temps d'insertion pour ABR (ordre trie): " + abrSortedInsertTime + " s");
        writer.println("Temps d'insertion pour ABR (ordre trie): " + abrSortedInsertTime + " s");
        writer.flush();  // S'assurer que les donnees sont ecrites dans le fichier immediatement

        // Mesurer le temps de recherche pour les clefs 0,...,2n-1
        int n = size;
        long arnSearchTime = 0;
        long abrSearchTime = 0;

        // Recherche des clefs dans ARN
        startTime = System.nanoTime();
        for (int i = 0; i < 2 * n; i++) {
            arn.contains(i);
        }
        endTime = System.nanoTime();
        arnSearchTime = endTime - startTime;
        System.out.println("Temps de recherche pour ARN: " + arnSearchTime / 1_000_000_000.0 + " s");
        writer.println("Temps de recherche pour ARN: " + arnSearchTime / 1_000_000_000.0 + " s");
        writer.flush();  // S'assurer que les donnees sont ecrites dans le fichier immediatement

        // Recherche des clefs dans ABR
        startTime = System.nanoTime();
        for (int i = 0; i < 2 * n; i++) {
            abr.contains(i);
        }
        endTime = System.nanoTime();
        abrSearchTime = endTime - startTime;
        System.out.println("Temps de recherche pour ABR: " + abrSearchTime / 1_000_000_000.0 + " s");
        writer.println("Temps de recherche pour ABR: " + abrSearchTime / 1_000_000_000.0 + " s");
        writer.flush();  // S'assurer que les donnees sont ecrites dans le fichier immediatement

        // Ligne vide pour separer les resultats des differents tests
        System.out.println();
        writer.println();
        writer.flush();  // S'assurer que les donnees sont ecrites dans le fichier immediatement
    }
}
