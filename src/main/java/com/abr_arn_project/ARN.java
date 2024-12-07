package com.abr_arn_project;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Implémentation de l'interface Collection basée sur les arbres rouges et noirs (ARN). 
 * Les éléments sont ordonnés selon l'ordre naturel ou selon un Comparateur fourni à la création.
 * 
 * @param <E> le type des éléments stockés dans l'arbre
 * 
 * @author Anes ABDELLI
 */
public class ARN<E> extends AbstractCollection<E> {
    private Noeud racine;
    private int taille;
    private Comparator<? super E> cmp;

    /**
     * Représente un noeud dans l'arbre.
     * Chaque noeud a une clé et une couleur associée ("R" pour rouge, "N" pour noir).
     */
    public class Noeud {
        private E cle;  // L'attribut cle est maintenant privé
        private String couleur; // "R" pour Rouge, "N" pour Noir
        Noeud gauche;
        Noeud droit;
        Noeud pere;

        /**
         * Crée un nouveau noeud contenant une clé et une couleur.
         *
         * @param cle la clé stockée dans le noeud
         * @param couleur la couleur du noeud ("R" ou "N")
         */
        Noeud(E cle, String couleur) {
            this.cle = cle;
            this.couleur = couleur;
            this.gauche = null;
            this.droit = null;
            this.pere = null;
        }

        /**
         * Getter pour l'attribut cle.
         *
         * @return la clé du noeud
         */
        public E getCle() {
            return cle;
        }

        /**
         * Getter pour l'attribut couleur.
         *
         * @return la couleur du noeud
         */
        public String getCouleur() {
            return couleur;
        }

        /**
         * Trouve le noeud avec la clé minimale dans le sous-arbre actuel.
         *
         * @return le noeud avec la clé minimale
         */
        Noeud minimum() {
            Noeud x = this;
            while (x.gauche != null) {
                x = x.gauche;
            }
            return x;
        }

        /**
         * Trouve le successeur du noeud courant dans l'ordre des clés.
         *
         * @return le noeud successeur ou null si ce noeud est le plus grand
         */
        Noeud suivant() {
            Noeud x = this;
            if (x.droit != null) {
                return x.droit.minimum();
            }
            Noeud y = x.pere;
            while (y != null && x == y.droit) {
                x = y;
                y = y.pere;
            }
            return y;
        }
    }

    /**
     * Constructeur par défaut. Les éléments seront ordonnés selon l'ordre naturel.
     */
    public ARN() {
        racine = null;
        taille = 0;
        cmp = (x, y) -> ((Comparable<E>) x).compareTo(y);
    }

    /**
     * Constructeur avec comparateur. Les éléments seront ordonnés selon le comparateur fourni.
     *
     * @param cmp le comparateur à utiliser
     */
    public ARN(Comparator<? super E> cmp) {
        this.cmp = cmp;
        racine = null;
        taille = 0;
    }

    /**
     * Constructeur à partir d'une collection. Les éléments sont copiés et ordonnés selon l'ordre naturel.
     *
     * @param c la collection source
     */
    public ARN(Collection<? extends E> c) {
        this();
        addAll(c);
    }

    @Override
    public Iterator<E> iterator() {
        return new ARNIterator();
    }

    @Override
    public int size() {
        return taille;
    }

    /**
     * Recherche une clé dans l'arbre.
     *
     * @param o la clé à rechercher
     * @return le noeud contenant la clé ou null si la clé est absente
     */
    public Noeud rechercher(Object o) {
        Noeud x = racine;
        while (x != null && cmp.compare(x.cle, (E) o) != 0) {
            x = cmp.compare(x.cle, (E) o) > 0 ? x.gauche : x.droit;
        }
        return x;
    }

    /**
     * Recherche et insère une clé dans l'arbre.
     *
     * @param o la clé à ajouter
     * @return true si l'ajout a réussi, false si la clé existe déjà
     */
    @Override
    public boolean add(Object o) {
        Noeud z = new Noeud((E) o, "R");  // Nouveau noeud rouge par défaut
        Noeud x = racine;
        Noeud y = null;

        while (x != null) {
            y = x;
            int cmpResult = cmp.compare(x.cle, z.cle);
            if (cmpResult == 0) {
                return false;
            }
            x = cmpResult > 0 ? x.gauche : x.droit;
        }

        if (y == null) {
            racine = z;
        } else if (cmp.compare(y.cle, z.cle) > 0) {
            y.gauche = z;
        } else {
            y.droit = z;
        }
        z.pere = y;
        taille++;
        fixAfterInsertion(z); // Ajuste les couleurs après insertion
        return true;
    }

    /**
     * Fixe les couleurs des noeuds pour maintenir les propriétés de l'ARN après l'insertion.
     *
     * @param z le noeud récemment ajouté
     */
    private void fixAfterInsertion(Noeud z) {
        while (z != racine && "R".equals(z.pere.couleur)) {
            if (z.pere == z.pere.pere.gauche) {
                Noeud y = z.pere.pere.droit;
                if (y != null && "R".equals(y.couleur)) {
                    z.pere.couleur = "N";
                    y.couleur = "N";
                    z.pere.pere.couleur = "R";
                    z = z.pere.pere;
                } else {
                    if (z == z.pere.droit) {
                        z = z.pere;
                        rotateLeft(z);
                    }
                    z.pere.couleur = "N";
                    z.pere.pere.couleur = "R";
                    rotateRight(z.pere.pere);
                }
            } else {
                Noeud y = z.pere.pere.gauche;
                if (y != null && "R".equals(y.couleur)) {
                    z.pere.couleur = "N";
                    y.couleur = "N";
                    z.pere.pere.couleur = "R";
                    z = z.pere.pere;
                } else {
                    if (z == z.pere.gauche) {
                        z = z.pere;
                        rotateRight(z);
                    }
                    z.pere.couleur = "N";
                    z.pere.pere.couleur = "R";
                    rotateLeft(z.pere.pere);
                }
            }
        }
        racine.couleur = "N"; // La racine est toujours noire
    }

    /**
     * Effectue une rotation gauche autour d'un noeud.
     *
     * @param x le noeud autour duquel effectuer la rotation
     */
    private void rotateLeft(Noeud x) {
        Noeud y = x.droit;
        x.droit = y.gauche;
        if (y.gauche != null) {
            y.gauche.pere = x;
        }
        y.pere = x.pere;
        if (x.pere == null) {
            racine = y;
        } else if (x == x.pere.gauche) {
            x.pere.gauche = y;
        } else {
            x.pere.droit = y;
        }
        y.gauche = x;
        x.pere = y;
    }

    /**
     * Effectue une rotation droite autour d'un noeud.
     *
     * @param x le noeud autour duquel effectuer la rotation
     */
    private void rotateRight(Noeud x) {
        Noeud y = x.gauche;
        x.gauche = y.droit;
        if (y.droit != null) {
            y.droit.pere = x;
        }
        y.pere = x.pere;
        if (x.pere == null) {
            racine = y;
        } else if (x == x.pere.droit) {
            x.pere.droit = y;
        } else {
            x.pere.gauche = y;
        }
        y.droit = x;
        x.pere = y;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c) {
            if (add(e)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean contains(Object o) {
        return rechercher(o) != null;
    }

    @Override
    public boolean remove(Object o) {
        // Suppression d'un élément à implémenter
        return false;
    }

    @Override
    public void clear() {
        racine = null;
        taille = 0;
    }

    private class ARNIterator implements Iterator<E> {
        private Noeud courant = racine;

        @Override
        public boolean hasNext() {
            return courant != null;
        }

        @Override
        public E next() {
            E res = courant.cle;
            courant = courant.suivant();
            return res;
        }
    }
}
