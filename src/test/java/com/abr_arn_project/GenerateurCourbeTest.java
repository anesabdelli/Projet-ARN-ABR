package com.abr_arn_project;

import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import static org.junit.jupiter.api.Assertions.*;

class GenerateurCourbeTest {

    @Test
    void testCreerInsertionDataset() {
        // Vérifier que les données d'insertion sont bien créées
        DefaultCategoryDataset dataset = GenerateurCourbe.creerInsertionDataset();

        // Vérification de l'existence de quelques données
        assertNotNull(dataset);
        assertEquals(4, dataset.getRowCount());  // On a 4 séries (ARN, ABR, pour chaque taille)
        assertTrue(dataset.getColumnCount() > 0); // Au moins une taille dans les colonnes
        assertEquals(0.1825444, dataset.getValue("ARN (ordre aleatoire)", "1000000"));
        assertEquals(0.3839244, dataset.getValue("ABR (ordre trie)", "1000000"));
    }

    @Test
    void testCreerSearchDataset() {
        // Vérifier que les données de recherche sont bien créées
        DefaultCategoryDataset dataset = GenerateurCourbe.creerSearchDataset();

        // Vérification de l'existence de quelques données
        assertNotNull(dataset);
        assertEquals(2, dataset.getRowCount());  // On a 2 séries (ARN, ABR)
        assertTrue(dataset.getColumnCount() > 0); // Au moins une taille dans les colonnes
        assertEquals(0.1554959, dataset.getValue("ARN", "1000000"));
        assertEquals(4.2939454, dataset.getValue("ABR", "10000000"));
    }

    @Test
    void testGenerationGraphiqueInsertion() throws IOException {
        // Simuler la génération du graphique pour l'insertion
        DefaultCategoryDataset dataset = GenerateurCourbe.creerInsertionDataset();
        JFreeChart chart = ChartFactory.createLineChart(
                "Comparaison du temps d'insertion",
                "Taille",
                "Temps (secondes)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // Vérifier que le graphique est bien créé
        assertNotNull(chart);

        // Sauvegarder l'image générée (dans un fichier temporaire pour la validation)
        File imageFile = new File("test_insertion_comparison_chart.png");
        ImageIO.write(chart.createBufferedImage(800, 600), "PNG", imageFile);

        // Vérifier que l'image est bien sauvegardée
        assertTrue(imageFile.exists());
        imageFile.delete();  // Nettoyage après le test
    }

    @Test
    void testGenerationGraphiqueRecherche() throws IOException {
        // Simuler la génération du graphique pour la recherche
        DefaultCategoryDataset dataset = GenerateurCourbe.creerSearchDataset();
        JFreeChart chart = ChartFactory.createLineChart(
                "Comparaison du temps de recherche",
                "Taille",
                "Temps (secondes)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // Vérifier que le graphique est bien créé
        assertNotNull(chart);

        // Sauvegarder l'image générée (dans un fichier temporaire pour la validation)
        File imageFile = new File("test_search_comparison_chart.png");
        ImageIO.write(chart.createBufferedImage(800, 600), "PNG", imageFile);

        // Vérifier que l'image est bien sauvegardée
        assertTrue(imageFile.exists());
        imageFile.delete();  // Nettoyage après le test
    }

    @Test
    void testMain() throws IOException {
        // Exécuter la méthode main directement
        GenerateurCourbe.main(new String[0]);

        // Vérifier que les fichiers images ont été générés
        File insertionFile = new File("insertion_comparison_chart.png");
        File searchFile = new File("search_comparison_chart.png");

        assertTrue(insertionFile.exists());
        assertTrue(searchFile.exists());

        // Nettoyage des fichiers après le test
        insertionFile.delete();
        searchFile.delete();
    }
}
