package com.abr_arn_project;

/**
 * Implémentation de l'interface Collection basée sur les arbres binaires de recherche (ABR). 
 * Les éléments sont ordonnés selon l'ordre naturel ou selon un Comparateur fourni à la création.
 * 
 * @param <E> le type des éléments stockés dans l'arbre
 * 
 * @author Anes ABDELLI
 */

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

public class ABR<E> extends AbstractCollection<E> {

    private Noeud racine;
    private int taille;
    private Comparator<? super E> cmp;

    /**
     * Représente un noeud dans l'arbre.
     */
    public class Noeud {
        private E cle;  // L'attribut cle est maintenant privé
        Noeud gauche;
        Noeud droit;
        Noeud pere;

        /**
         * Crée un nouveau noeud contenant une clé.
         *
         * @param cle la clé stockée dans le noeud
         */
        Noeud(E cle) {
            this.cle = cle;
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
    public ABR() {
        racine = null;
        taille = 0;
        cmp = (x, y) -> ((Comparable<E>) x).compareTo(y);
    }

    /**
     * Constructeur avec comparateur. Les éléments seront ordonnés selon le comparateur fourni.
     *
     * @param cmp le comparateur à utiliser
     */
    public ABR(Comparator<? super E> cmp) {
        this.cmp = cmp;
        racine = null;
        taille = 0;
    }

    /**
     * Constructeur à partir d'une collection. Les éléments sont copiés et ordonnés selon l'ordre naturel.
     *
     * @param c la collection source
     */
    public ABR(Collection<? extends E> c) {
        this();
        addAll(c);
    }

    @Override
    public Iterator<E> iterator() {
        return new ABRIterator();
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
     * Supprime un noeud de l'arbre.
     *
     * @param z le noeud à supprimer
     * @return le successeur logique du noeud supprimé
     */
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

    @Override
    public boolean add(Object o) {
        Noeud z = new Noeud((E) o);
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
        return true;
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

    /**
     * Représente un itérateur pour l'arbre binaire de recherche.
     */
    private class ABRIterator implements Iterator<E> {
        private Noeud prec, suiv;

        public ABRIterator() {
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
        buf.append("-- " + x.cle);
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
