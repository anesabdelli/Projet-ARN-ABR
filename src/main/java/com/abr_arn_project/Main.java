package com.abr_arn_project;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        
        // Test for ABR
        System.out.println("=================================");
        System.out.println("          Test de l'ABR          ");
        System.out.println("=================================\n");
        
        // Create an ABR of integers
        ABR<Integer> arbreABR = new ABR<>();
        
        // Add elements to the tree
        System.out.println("Ajout de quelques elements a l'arbre ABR : ");
        arbreABR.add(10);
        arbreABR.add(5);
        arbreABR.add(15);
        arbreABR.add(3);
        arbreABR.add(7);
        arbreABR.add(13);
        arbreABR.add(18);

        // Display the tree
        System.out.println("\nL'arbre ABR contient les elements suivants : 10, 5, 15, 3, 7, 13, 18");
        System.out.println("Affichage de l'arbre ABR : ");
        System.out.println(arbreABR);

        // Test if certain elements exist in the tree
        System.out.println("\nContient les elements? ");
        System.out.println("Contient 7 : " + arbreABR.contains(7));
        System.out.println("Contient 20 : " + arbreABR.contains(20));

        // Test the iterator
        System.out.println("\nIteration sur les elements de l'arbre ABR (dans l'ordre) : ");
        for (Integer valeur : arbreABR) {
            System.out.print(valeur + " ");
        }

        // Test the removal of an element (example with 7)
        System.out.println("\n\nSuppression de l'element 7 dans l'ABR : ");
        arbreABR.remove(7);
        System.out.println("L'arbre ABR contient les elements suivants apres suppression de 7 : 10, 5, 15, 3, 13, 18");
        System.out.println(arbreABR);

        // Test the addAll method with a collection of elements
        System.out.println("\nAjout d'une nouvelle collection d'elements dans l'ABR : ");
        arbreABR.addAll(Arrays.asList(12, 4, 8));
        System.out.println("L'arbre ABR contient les elements suivants apres ajout : 10, 5, 15, 3, 13, 18, 12, 4, 8");
        System.out.println(arbreABR);

        // Display the size of the tree
        System.out.println("\nTaille de l'arbre ABR : " + arbreABR.size());

        // Test the clear() method to empty the tree
        System.out.println("\nVider l'arbre ABR avec clear()...");
        arbreABR.clear();
        System.out.println("Arbre ABR apres clear : " + arbreABR);
        System.out.println("Taille apres clear de l'ABR : " + arbreABR.size());

        
        // Test for ARN (Red-Black Tree)
        System.out.println("=================================");
        System.out.println("          Test de l'ARN          ");
        System.out.println("=================================\n");

        // Create an ARN of integers
        ARN<Integer> arbreARN = new ARN<>();
        
        // Add elements to the tree
        System.out.println("Ajout de quelques elements a l'arbre ARN : ");
        arbreARN.add(10);
        arbreARN.add(5);
        arbreARN.add(15);
        arbreARN.add(3);
        arbreARN.add(7);
        arbreARN.add(13);
        arbreARN.add(18);

        // Display the tree
        System.out.println("\nL'arbre ARN contient les elements suivants : 10, 5, 15, 3, 7, 13, 18");
        System.out.println("Affichage de l'arbre ARN : ");
        System.out.println(arbreARN);

        // Test if certain elements exist in the tree
        System.out.println("\nContient les elements? ");
        System.out.println("Contient 7 : " + arbreARN.contains(7));
        System.out.println("Contient 20 : " + arbreARN.contains(20));

        // Test the iterator
        System.out.println("\nIteration sur les elements de l'arbre ARN (dans l'ordre) : ");
        for (Integer valeur : arbreARN) {
            System.out.print(valeur + " ");
        }

        // Test the removal of an element (example with 7)
        System.out.println("\n\nSuppression de l'element 7 dans l'ARN : ");
        arbreARN.remove(7);
        System.out.println("L'arbre ARN contient les elements suivants apres suppression de 7 : 10, 5, 15, 3, 13, 18");
        System.out.println(arbreARN);

        // Test the addAll method with a collection of elements
        System.out.println("\nAjout d'une nouvelle collection d'elements dans l'ARN : ");
        arbreARN.addAll(Arrays.asList(12, 4, 8));
        System.out.println("L'arbre ARN contient les elements suivants apres ajout : 10, 5, 15, 3, 13, 18, 12, 4, 8");
        System.out.println(arbreARN);

        // Display the size of the tree
        System.out.println("\nTaille de l'arbre ARN : " + arbreARN.size());

        // Test the clear() method to empty the tree
        System.out.println("\nVider l'arbre ARN avec clear()...");
        arbreARN.clear();
        System.out.println("Arbre ARN apres clear : " + arbreARN);
        System.out.println("Taille apres clear de l'ARN : " + arbreARN.size());
    }
}
