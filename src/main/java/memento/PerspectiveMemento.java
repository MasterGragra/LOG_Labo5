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
     */
    public PerspectiveMemento(double facteur, int x, int y) {
        this.facteurEchelle = facteur;
        this.positionX = x;
        this.positionY = y;
    }

//-------------------getter-------------------------------------------------------------

    public double getFacteurEchelle() {
        return facteurEchelle;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }
}