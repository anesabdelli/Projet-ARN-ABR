package com.abr_arn_project;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ABRTest {
    private ABR<Integer> abr;

    @BeforeEach
    void setUp() {
        abr = new ABR<>();
    }

    @Test
    void testAdd() {
        assertTrue(abr.add(10));
        assertTrue(abr.add(20));
        assertFalse(abr.add(10)); // Duplicate addition
        assertEquals(2, abr.size());
    }

    @Test
    void testContains() {
        abr.add(10);
        abr.add(20);
        assertTrue(abr.contains(10));
        assertTrue(abr.contains(20));
        assertFalse(abr.contains(30));
    }

    @Test
    void testRemove() {
        abr.add(10);
        abr.add(20);
        abr.add(30);
        assertTrue(abr.remove(20));
        assertFalse(abr.contains(20));
        assertEquals(2, abr.size());
        assertFalse(abr.remove(50)); // Non-existent element
    }

    @Test
    void testRemoveFromEmpty() {
        assertFalse(abr.remove(10)); // Removing from an empty tree
    }

    @Test
    void testClear() {
        abr.add(10);
        abr.add(20);
        abr.clear();
        assertEquals(0, abr.size());
        assertFalse(abr.contains(10));
    }

    @Test
    void testIterator() {
        abr.add(10);
        abr.add(20);
        abr.add(30);
        Iterator<Integer> it = abr.iterator();

        assertTrue(it.hasNext());
        assertEquals(10, it.next());
        assertEquals(20, it.next());
        assertEquals(30, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    void testAddAll() {
        List<Integer> toAdd = Arrays.asList(10, 20, 30);
        assertTrue(abr.addAll(toAdd));
        assertEquals(3, abr.size());
        assertTrue(abr.contains(10));
        assertTrue(abr.contains(20));
        assertTrue(abr.contains(30));
    }

    @Test
    void testToString() {
        abr.addAll(Arrays.asList(20, 10, 30, 5, 15, 25, 35));
        String treeRepresentation = abr.toString();
        assertNotNull(treeRepresentation);
        assertFalse(treeRepresentation.isEmpty());
        System.out.println(treeRepresentation); // Optional: print the tree
    }

    @Test
    void testSize() {
        assertEquals(0, abr.size());
        abr.add(10);
        assertEquals(1, abr.size());
        abr.add(20);
        abr.add(30);
        assertEquals(3, abr.size());
        abr.remove(20);
        assertEquals(2, abr.size());
    }

    @Test
    void testConstructorWithComparator() {
        ABR<Integer> abrWithComparator = new ABR<>(Integer::compareTo);
        assertTrue(abrWithComparator.add(10));
        assertTrue(abrWithComparator.add(5));
        assertTrue(abrWithComparator.add(15));
        assertEquals(3, abrWithComparator.size());
    }

    @Test
    void testConstructorWithCollection() {
        List<Integer> elements = Arrays.asList(10, 20, 30, 40);
        ABR<Integer> abrWithCollection = new ABR<>(elements);
        assertEquals(4, abrWithCollection.size());
        assertTrue(abrWithCollection.contains(10));
        assertTrue(abrWithCollection.contains(40));
    }

    @Test
    void testSupprimerNode() {
        abr.add(10);
        abr.add(20);
        abr.add(30);
        assertTrue(abr.remove(20));
        assertFalse(abr.contains(20));
        assertEquals(2, abr.size());

        // Testing edge cases like removing the root node
        assertTrue(abr.remove(10));
        assertFalse(abr.contains(10));
        assertEquals(1, abr.size());
    }
}
