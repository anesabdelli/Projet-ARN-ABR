package com.abr_arn_project;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MainTest {

    private ABR<Integer> arbreABR;
    private ARN<Integer> arbreARN;

    @BeforeEach
    void setUp() {
        // Initialisation des arbres avant chaque test
        arbreABR = new ABR<>();
        arbreARN = new ARN<>();
    }

    @Test
    void testABRInsertion() {
        // Test de l'ajout d'éléments dans l'ABR
        arbreABR.add(10);
        arbreABR.add(5);
        arbreABR.add(15);
        arbreABR.add(3);
        arbreABR.add(7);
        arbreABR.add(13);
        arbreABR.add(18);

        assertTrue(arbreABR.contains(7));
        assertTrue(arbreABR.contains(10));
        assertTrue(arbreABR.contains(15));
        assertFalse(arbreABR.contains(20));
    }

    @Test
    void testABRRemove() {
        // Test de la suppression d'un élément dans l'ABR
        arbreABR.add(10);
        arbreABR.add(5);
        arbreABR.add(15);
        arbreABR.add(7);

        arbreABR.remove(7);

        assertFalse(arbreABR.contains(7));
        assertTrue(arbreABR.contains(10));
    }

    @Test
    void testABRAddAll() {
        // Test de l'ajout d'une collection d'éléments dans l'ABR
        arbreABR.add(10);
        arbreABR.addAll(Arrays.asList(5, 15, 3, 7));

        assertTrue(arbreABR.contains(5));
        assertTrue(arbreABR.contains(15));
        assertTrue(arbreABR.contains(3));
        assertTrue(arbreABR.contains(7));
    }

    @Test
    void testABRSize() {
        // Test de la taille de l'ABR
        arbreABR.add(10);
        arbreABR.add(5);
        arbreABR.add(15);

        assertEquals(3, arbreABR.size());
    }

    @Test
    void testABRClear() {
        // Test de la méthode clear() dans l'ABR
        arbreABR.add(10);
        arbreABR.add(5);
        arbreABR.clear();

        assertEquals(0, arbreABR.size());
    }

    @Test
    void testARNInsertion() {
        // Test de l'ajout d'éléments dans l'ARN
        arbreARN.add(10);
        arbreARN.add(5);
        arbreARN.add(15);
        arbreARN.add(3);
        arbreARN.add(7);
        arbreARN.add(13);
        arbreARN.add(18);

        assertTrue(arbreARN.contains(7));
        assertTrue(arbreARN.contains(10));
        assertTrue(arbreARN.contains(15));
        assertFalse(arbreARN.contains(20));
    }

    @Test
    void testARNRemove() {
        // Test de la suppression d'un élément dans l'ARN
        arbreARN.add(10);
        arbreARN.add(5);
        arbreARN.add(15);
        arbreARN.add(7);

        arbreARN.remove(7);

        assertFalse(arbreARN.contains(7));
        assertTrue(arbreARN.contains(10));
    }

    @Test
    void testARNAddAll() {
        // Test de l'ajout d'une collection d'éléments dans l'ARN
        arbreARN.add(10);
        arbreARN.addAll(Arrays.asList(5, 15, 3, 7));

        assertTrue(arbreARN.contains(5));
        assertTrue(arbreARN.contains(15));
        assertTrue(arbreARN.contains(3));
        assertTrue(arbreARN.contains(7));
    }

    @Test
    void testARNSize() {
        // Test de la taille de l'ARN
        arbreARN.add(10);
        arbreARN.add(5);
        arbreARN.add(15);

        assertEquals(3, arbreARN.size());
    }

    @Test
    void testARNClear() {
        // Test de la méthode clear() dans l'ARN
        arbreARN.add(10);
        arbreARN.add(5);
        arbreARN.clear();

        assertEquals(0, arbreARN.size());
    }

    // Optionnel: Test des itérateurs
    @Test
    void testABRIterator() {
        arbreABR.add(10);
        arbreABR.add(5);
        arbreABR.add(15);
        arbreABR.add(3);

        // Vérifier l'itération sur les éléments
        StringBuilder sb = new StringBuilder();
        for (Integer valeur : arbreABR) {
            sb.append(valeur).append(" ");
        }

        assertEquals("3 5 10 15 ", sb.toString());
    }

    @Test
    void testARNIterator() {
        arbreARN.add(10);
        arbreARN.add(5);
        arbreARN.add(15);
        arbreARN.add(3);

        // Vérifier l'itération sur les éléments
        StringBuilder sb = new StringBuilder();
        for (Integer valeur : arbreARN) {
            sb.append(valeur).append(" ");
        }

        assertEquals("3 5 10 15 ", sb.toString());
    }
}
