package com.abr_arn_project;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

public class ARN<E> extends AbstractCollection<E> {
    private Noeud racine;
    private int taille;
    private Comparator<? super E> cmp;

    public class Noeud {
        private E cle;
        private String couleur; // "R" for Red, "N" for Black
        Noeud gauche;
        Noeud droit;
        Noeud pere;

        Noeud(E cle, String couleur) {
            this.cle = cle;
            this.couleur = couleur;
            this.gauche = null;
            this.droit = null;
            this.pere = null;
        }

        public E getCle() {
            return cle;
        }

        public String getCouleur() {
            return couleur;
        }

        Noeud minimum() {
            Noeud x = this;
            while (x.gauche != null) {
                x = x.gauche;
            }
            return x;
        }

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

    public ARN() {
        racine = null;
        taille = 0;
        cmp = (x, y) -> ((Comparable<E>) x).compareTo(y);
    }

    public ARN(Comparator<? super E> cmp) {
        this.cmp = cmp;
        racine = null;
        taille = 0;
    }

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

    public Noeud rechercher(Object o) {
        Noeud x = racine;
        while (x != null && cmp.compare(x.cle, (E) o) != 0) {
            x = cmp.compare(x.cle, (E) o) > 0 ? x.gauche : x.droit;
        }
        return x;
    }

    @Override
    public boolean add(Object o) {
        Noeud z = new Noeud((E) o, "R");  // New node is initially red
        Noeud x = racine;
        Noeud y = null;
        
        // gestion d'erreur
        if (o == null) {
        throw new NullPointerException("L'élément à insérer ne peut pas être null");
    }

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
        // Fix the colors after insertion (simplified, needs to be adjusted for the full RB tree property)
        fixAfterInsertion(z);
        return true;
    }

    private void fixAfterInsertion(Noeud z) {
        // Fix the color of nodes to maintain Red-Black Tree properties
        // For simplicity, this is not a full fixup, just a placeholder to mark color handling
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
        racine.couleur = "N"; // root is always black
    }

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
        Noeud x = rechercher(o);
        if (x != null) {
            supprimer(x);
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        racine = null;
        taille = 0;
    }

    public Noeud supprimer(Noeud z) {
        Noeud a;
        Noeud b = (z.gauche == null || z.droit == null) ? z : z.suivant();
        a = (b.gauche != null) ? b.gauche : b.droit;

        if (a != null) {
            a.pere = b.pere;
        }
        if (b.pere == null) {
            racine = a;
        } else if (b == b.pere.gauche) {
            b.pere.gauche = a;
        } else {
            b.pere.droit = a;
        }
        if (b != z) {
            z.cle = b.cle;
        }
        taille--;
        return b;
    }

    private class ARNIterator implements Iterator<E> {
        private Noeud prec, suiv;

        public ARNIterator() {
            prec = null;
            suiv = (racine == null) ? null : racine.minimum();
        }

        @Override
        public boolean hasNext() {
            return suiv != null;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            prec = suiv;
            suiv = suiv.suivant();
            return prec.cle;
        }

        @Override
        public void remove() {
            if (prec == null) {
                throw new IllegalStateException();
            }
            Noeud nodeToRemove = prec;
            suiv = supprimer(nodeToRemove);
            prec = null;
        }
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        toString(racine, buf, "", maxStrLen(racine));
        return buf.toString();
    }

    private void toString(Noeud x, StringBuffer buf, String path, int len) {
        if (x == null) return;
        toString(x.droit, buf, path + "D", len);
        for (int i = 0; i < path.length(); i++) {
            for (int j = 0; j < len + 6; j++) buf.append(' ');
            char c = (i == path.length() - 1) ? '+' : (path.charAt(i) != path.charAt(i + 1) ? '|' : ' ');
            buf.append(c);
        }
        buf.append("-- " + x.cle + "(" + x.couleur + ")");
        if (x.gauche != null || x.droit != null) {
            buf.append(" --");
            for (int j = x.cle.toString().length(); j < len; j++) buf.append('-');
            buf.append('|');
        }
        buf.append("\n");
        toString(x.gauche, buf, path + "G", len);
    }

    private int maxStrLen(Noeud x) {
        return x == null ? 0 : Math.max(x.cle.toString().length(),
                Math.max(maxStrLen(x.gauche), maxStrLen(x.droit)));
    }
}
