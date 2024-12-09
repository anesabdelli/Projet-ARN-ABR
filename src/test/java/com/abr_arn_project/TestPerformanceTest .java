package com.abr_arn_project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestPerformanceTest {

    private TestPerformance testPerformance;

    @BeforeEach
    void setUp() {
        testPerformance = new TestPerformance();
    }

    @Test
    void testExecuterTest() throws IOException {
        // Créer des fichiers temporaires pour tester sans écraser les fichiers existants
        File csvFile = new File("test_performance_results.csv");
        File txtFile = new File("test_performance_results.txt");

        // S'assurer que les fichiers sont bien créés
        try (PrintWriter csvWriter = new PrintWriter(new FileWriter(csvFile));
             BufferedWriter txtWriter = new BufferedWriter(new FileWriter(txtFile))) {

            testPerformance.executerTest(1_000_000, csvWriter, txtWriter);

            // Vérifier que les fichiers ne sont pas vides après l'exécution du test
            assertTrue(csvFile.length() > 0, "Le fichier CSV ne doit pas être vide.");
            assertTrue(txtFile.length() > 0, "Le fichier TXT ne doit pas être vide.");
        }

        // Vérifier que les fichiers ont été bien générés et leur contenu
        assertTrue(csvFile.exists(), "Le fichier CSV devrait exister.");
        assertTrue(txtFile.exists(), "Le fichier TXT devrait exister.");
        

        // Lire le fichier TXT et vérifier qu'il contient bien des informations sur le test
        try (BufferedReader reader = new BufferedReader(new FileReader(txtFile))) {
            String line = reader.readLine();
            assertNotNull(line, "Le fichier TXT devrait contenir des données.");
        }

        // Nettoyage des fichiers après les tests
        csvFile.delete();
        txtFile.delete();
    }

    @Test
    void testMain() {
        // On vérifie que la méthode main fonctionne sans exception
        assertDoesNotThrow(() -> TestPerformance.main(new String[]{}), "La méthode main a généré une exception.");
    }
}
