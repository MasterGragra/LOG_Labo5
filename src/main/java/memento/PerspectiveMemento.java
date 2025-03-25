package memento;

import java.io.Serializable;

/**
 * Classe représentant un memento pour la perspective
 */
public class PerspectiveMemento implements Serializable {
    private final double facteurEchelle; // Facteur d'échelle (niveau de zoom)
    private final int positionX; // Position en X
    private final int positionY; // Position en Y

    /**
     * Constructeur
     * @param facteur le facteur d'échelle
     * @param x la position en X
     * @param y la position en Y
     */
    public PerspectiveMemento(double facteur, int x, int y) {
        this.facteurEchelle = facteur;
        this.positionX = x;
        this.positionY = y;
    }

//-------------------getter-------------------------------------------------------------

    /**
     * Retourne le facteur d'échelle
     * @return le facteur d'échelle
     */
    public double getFacteurEchelle() {
        return facteurEchelle;
    }

    /**
     * Retourne la position en X
     * @return la position en X
     */
    public int getPositionX() {
        return positionX;
    }

    /**
     * Retourne la position en Y
     * @return la position en Y
     */
    public int getPositionY() {
        return positionY;
    }
}