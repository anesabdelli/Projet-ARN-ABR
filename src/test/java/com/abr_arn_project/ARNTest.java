package com.abr_arn_project;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ARNTest {
    private ARN<Integer> arn;

    @BeforeEach
    void setUp() {
        arn = new ARN<>();
    }

    // --- 1. Tests de base ---
    @Test
    void testInsertionSimple() {
        assertTrue(arn.add(10));
        assertTrue(arn.add(20));
        assertTrue(arn.add(30));
        assertEquals(3, arn.size());
        assertTrue(arn.contains(10));
        assertTrue(arn.contains(20));
        assertTrue(arn.contains(30));
    }

    @Test
    void testInsertionDoublons() {
        assertTrue(arn.add(10));
        assertFalse(arn.add(10));  // Pas de doublons
        assertEquals(1, arn.size());
    }

    @Test
    void testSuppressionSimple() {
        arn.add(10);
        arn.add(20);
        arn.add(30);

        assertTrue(arn.remove(20));
        assertFalse(arn.contains(20));
        assertEquals(2, arn.size());
    }

    @Test
    void testSuppressionNonExistante() {
        assertFalse(arn.remove(10));
    }

    @Test
    void testSuppressionRacine() {
        arn.add(10);
        assertTrue(arn.remove(10));
        assertEquals(0, arn.size());
        assertFalse(arn.contains(10));
    }

    // --- 2. Tests des propriétés de l'ARN ---
    @Test
    void testRacineNoire() {
        arn.add(10);
        assertEquals("N", arn.rechercher(10).getCouleur());
    }

    @Test
    void testPasDeuxRougesAdjacents() {
        arn.addAll(Arrays.asList(10, 5, 15, 3, 7, 13, 17));
        ARN<Integer>.Noeud n = arn.rechercher(7);
        assertNotEquals(n.pere.getCouleur(), n.getCouleur());
    }

    // --- 3. Tests structurels ---
    @Test
    void testRotations() {
        arn.addAll(Arrays.asList(10, 20, 30, 25, 15));
        assertTrue(arn.contains(25));
    }

    // --- 4. Parcours infixe avec itérateur ---
    @Test
    void testParcoursAvecIterateur() {
        arn.addAll(Arrays.asList(10, 20, 5, 15));
        Iterator<Integer> it = arn.iterator();

        assertTrue(it.hasNext());
        assertEquals(5, it.next());
        assertEquals(10, it.next());
        assertEquals(15, it.next());
        assertEquals(20, it.next());
        assertFalse(it.hasNext());
    }

    // --- 5. Tests de création ---
    @Test
    void testConstructeurAvecCollection() {
        List<Integer> elements = Arrays.asList(10, 20, 30, 40);
        ARN<Integer> arnAvecCollection = new ARN<>(elements);
        assertEquals(4, arnAvecCollection.size());
        assertTrue(arnAvecCollection.contains(10));
        assertTrue(arnAvecCollection.contains(40));
    }

    @Test
    void testConstructeurAvecComparateur() {
        ARN<Integer> arnInverse = new ARN<>(Comparator.reverseOrder());
        arnInverse.add(10);
        arnInverse.add(20);
        arnInverse.add(5);

        Iterator<Integer> it = arnInverse.iterator();
        assertEquals(20, it.next());
        assertEquals(10, it.next());
        assertEquals(5, it.next());
    }

    // --- 6. Tests supplémentaires ---
    @Test
    void testClear() {
        arn.addAll(Arrays.asList(10, 20, 30));
        arn.clear();
        assertEquals(0, arn.size());
        assertFalse(arn.contains(10));
    }

    @Test
    void testSize() {
        assertEquals(0, arn.size());
        arn.add(10);
        assertEquals(1, arn.size());
        arn.add(20);
        assertEquals(2, arn.size());
    }

    @Test
    void testToString() {
        arn.addAll(Arrays.asList(10, 5, 20, 15, 25));
        String arbreStr = arn.toString();
        assertNotNull(arbreStr);
        assertFalse(arbreStr.isEmpty());
        System.out.println(arbreStr);  // Affichage optionnel
    }

    @Test
    void testSuppressionDeuxEnfants() {
        arn.addAll(Arrays.asList(20, 10, 30, 5, 15, 25, 35));
        assertTrue(arn.remove(20));  // 20 a deux enfants
        assertFalse(arn.contains(20));
        assertEquals(6, arn.size());
    }

    // --- 7. Tests de robustesse ---
    @Test
    void testInsertionMassive() {
        for (int i = 1; i <= 10000; i++) {
            assertTrue(arn.add(i));
        }
        assertEquals(10000, arn.size());
    }

    @Test
    void testInsertionAleatoire() {
        Random rand = new Random();
        Set<Integer> ensemble = new HashSet<>();

        for (int i = 0; i < 1000; i++) {
            int valeur = rand.nextInt(10000);
            arn.add(valeur);
            ensemble.add(valeur);
        }

        for (int val : ensemble) {
            assertTrue(arn.contains(val));
        }
        assertEquals(ensemble.size(), arn.size());
    }

    @Test
    void testInsertionNull() {
        assertThrows(NullPointerException.class, () -> arn.add(null));
    }
}
