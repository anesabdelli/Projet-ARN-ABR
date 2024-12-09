package com.abr_arn_project;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class GenerateurCourbe {

    @SuppressWarnings("CallToPrintStackTrace")
    public static void main(String[] args) {
        // Creer les ensembles de donnees pour les graphiques
        DefaultCategoryDataset insertionDataset = creerInsertionDataset();
        DefaultCategoryDataset searchDataset = creerSearchDataset();

        // Creer les graphiques pour les temps d'insertion et de recherche
        JFreeChart insertionChart = ChartFactory.createLineChart(
                "Comparaison du temps d'insertion",  // Titre du graphique
                "Taille",                           // Etiquette de l'axe X
                "Temps (secondes)",                  // Etiquette de l'axe Y
                insertionDataset,                   // Ensemble de donnees
                PlotOrientation.VERTICAL,           // Orientation du graphique
                true,                               // Inclure la legende
                true,                               // Info-bulles
                false                               // URLs
        );

        JFreeChart searchChart = ChartFactory.createLineChart(
                "Comparaison du temps de recherche", // Titre du graphique
                "Taille",                           // Etiquette de l'axe X
                "Temps (secondes)",                  // Etiquette de l'axe Y
                searchDataset,                      // Ensemble de donnees
                PlotOrientation.VERTICAL,           // Orientation du graphique
                true,                               // Inclure la legende
                true,                               // Info-bulles
                false                               // URLs
        );

        // Sauvegarder les graphiques en tant qu'images PNG
        try {
            // Sauvegarder le graphique d'insertion
            ImageIO.write(insertionChart.createBufferedImage(800, 600), "PNG", new File("insertion_comparison_chart.png"));

            // Sauvegarder le graphique de recherche
            ImageIO.write(searchChart.createBufferedImage(800, 600), "PNG", new File("search_comparison_chart.png"));

            System.out.println("Les graphiques ont ete sauvegardes avec succes.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Creer l'ensemble de donnees pour les temps d'insertion
    public static DefaultCategoryDataset creerInsertionDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Exemple de donnees pour les temps d'insertion
        // Vous pouvez remplacer cela par les donnees reelles de vos tests
        dataset.addValue(0.1825444, "ARN (ordre aleatoire)", "1000000");
        dataset.addValue(0.1046882, "ABR (ordre aleatoire)", "1000000");
        dataset.addValue(0.0659499, "ARN (ordre trie)", "1000000");
        dataset.addValue(0.3839244, "ABR (ordre trie)", "1000000");

        dataset.addValue(0.9124675, "ARN (ordre aleatoire)", "10000000");
        dataset.addValue(0.5141975, "ABR (ordre aleatoire)", "10000000");
        dataset.addValue(0.2702057, "ARN (ordre trie)", "10000000");
        dataset.addValue(1.8836834, "ABR (ordre trie)", "10000000");

        dataset.addValue(4.9703206, "ARN (ordre aleatoire)", "100000000");
        dataset.addValue(5.261587, "ABR (ordre aleatoire)", "100000000");
        dataset.addValue(2.8569773, "ARN (ordre trie)", "100000000");
        dataset.addValue(13.4663485, "ABR (ordre trie)", "100000000");

        return dataset;
    }

    // Creer l'ensemble de donnees pour les temps de recherche
    public static DefaultCategoryDataset creerSearchDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Exemple de donnees pour les temps de recherche
        // Vous pouvez remplacer cela par les donnees reelles de vos tests
        dataset.addValue(0.1554959, "ARN", "1000000");
        dataset.addValue(0.7243651, "ABR", "1000000");

        dataset.addValue(0.6720922, "ARN", "10000000");
        dataset.addValue(4.2939454, "ABR", "10000000");

        dataset.addValue(6.3823293, "ARN", "100000000");
        dataset.addValue(41.1938857, "ABR", "100000000");

        return dataset;
    }
}
