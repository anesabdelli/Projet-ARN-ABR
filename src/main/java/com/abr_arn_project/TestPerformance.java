package com.abr_arn_project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TestPerformance {

    public static void main(String[] args) {
        // Créer un fichier CSV pour les courbes et un fichier TXT pour l'affichage détaillé
        try (
            PrintWriter csvWriter = new PrintWriter(new FileWriter("performance_results.csv"));
            BufferedWriter txtWriter = new BufferedWriter(new FileWriter("performance_results.txt"))
        ) {
            // Écrire l'en-tête du fichier CSV
            csvWriter.println("Taille,ARN_Insertion_Aleatoire,ABR_Insertion_Aleatoire,ARN_Insertion_Trie,ABR_Insertion_Trie,ARN_Recherche,ABR_Recherche");
            csvWriter.flush(); // S'assurer que l'en-tête est bien écrit dans le fichier CSV

            // Exécuter les tests avec différentes tailles de données
            executerTest(1_000_000, csvWriter, txtWriter);
            executerTest(10_000_000, csvWriter, txtWriter);
            executerTest(100_000_000, csvWriter, txtWriter);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void executerTest(int taille, PrintWriter csvWriter, BufferedWriter txtWriter) throws IOException {
        // Initialiser les arbres ARN et ABR
        ARN<Integer> arn = new ARN<>();
        ABR<Integer> abr = new ABR<>();

        // Générer des valeurs aléatoires
        ArrayList<Integer> valeursAleatoires = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < taille; i++) {
            valeursAleatoires.add(rand.nextInt(100));
        }

        // Cas moyen : Insertion des valeurs dans un ordre aléatoire
        Collections.shuffle(valeursAleatoires);
        long startTime = System.nanoTime();
        for (Integer value : valeursAleatoires) {
            arn.add(value);
        }
        long endTime = System.nanoTime();
        double arnInsertionAleatoire = (endTime - startTime) / 1_000_000_000.0;

        startTime = System.nanoTime();
        for (Integer value : valeursAleatoires) {
            abr.add(value);
        }
        endTime = System.nanoTime();
        double abrInsertionAleatoire = (endTime - startTime) / 1_000_000_000.0;

        // Cas pire : Insertion des valeurs dans un ordre trié
        ArrayList<Integer> valeursTriees = new ArrayList<>(valeursAleatoires);
        Collections.sort(valeursTriees);

        arn.clear();
        abr.clear();

        startTime = System.nanoTime();
        for (Integer value : valeursTriees) {
            arn.add(value);
        }
        endTime = System.nanoTime();
        double arnInsertionTriee = (endTime - startTime) / 1_000_000_000.0;

        startTime = System.nanoTime();
        for (Integer value : valeursTriees) {
            abr.add(value);
        }
        endTime = System.nanoTime();
        double abrInsertionTriee = (endTime - startTime) / 1_000_000_000.0;

        // Mesurer le temps de recherche pour les clés 0,...,2n-1
        int n = taille;
        long arnTempsRecherche = 0;
        long abrTempsRecherche = 0;

        startTime = System.nanoTime();
        for (int i = 0; i < 2 * n; i++) {
            arn.contains(i);
        }
        endTime = System.nanoTime();
        arnTempsRecherche = endTime - startTime;

        startTime = System.nanoTime();
        for (int i = 0; i < 2 * n; i++) {
            abr.contains(i);
        }
        endTime = System.nanoTime();
        abrTempsRecherche = endTime - startTime;

        // Enregistrer les résultats dans le fichier CSV pour les courbes
        csvWriter.println(taille + "," +
                arnInsertionAleatoire + "," +
                abrInsertionAleatoire + "," +
                arnInsertionTriee + "," +
                abrInsertionTriee + "," +
                arnTempsRecherche / 1_000_000_000.0 + "," +
                abrTempsRecherche / 1_000_000_000.0);
        csvWriter.flush();

        // Écrire l'affichage détaillé dans le fichier TXT
        txtWriter.write("Exécution du test avec " + taille + " éléments\n");
        txtWriter.write("Temps d'insertion pour ARN (ordre aléatoire): " + arnInsertionAleatoire + " s\n");
        txtWriter.write("Temps d'insertion pour ABR (ordre aléatoire): " + abrInsertionAleatoire + " s\n");
        txtWriter.write("Temps d'insertion pour ARN (ordre trié): " + arnInsertionTriee + " s\n");
        txtWriter.write("Temps d'insertion pour ABR (ordre trié): " + abrInsertionTriee + " s\n");
        txtWriter.write("Temps de recherche pour ARN: " + arnTempsRecherche / 1_000_000_000.0 + " s\n");
        txtWriter.write("Temps de recherche pour ABR: " + abrTempsRecherche / 1_000_000_000.0 + " s\n");
        txtWriter.write("\n");
        txtWriter.flush();
    }
}
