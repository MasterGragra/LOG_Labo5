package modele;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import memento.PerspectiveMemento;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import vue.Observer;

/**
 * Classe représentant une perspective d'affichage d'une image
 * Implémente Subject pour le pattern Observer et Serializable pour la persistance
 */
public class Perspective implements Subject, Serializable {
    private DoubleProperty facteurEchelle;
    private IntegerProperty positionX;
    private IntegerProperty positionY;
    private Image image;
    private final List<Observer> observers = new ArrayList<>();

    /**
     * Constructeur
     * @param image l'image associée à cette perspective
     */
    public Perspective(Image image) {
        this.image = image;
        this.facteurEchelle = new SimpleDoubleProperty(1.0);
        this.positionX = new SimpleIntegerProperty(0);
        this.positionY = new SimpleIntegerProperty(0);
    }

    /**
     * Crée un memento de l'état actuel de la perspective
     * @return un objet PerspectiveMemento contenant l'état actuel
     */
    public PerspectiveMemento createMemento() {
        return new PerspectiveMemento(facteurEchelle.get(), positionX.get(), positionY.get());
    }

    /**
     * Restaure l'état de la perspective à partir d'un memento
     * @param memento le memento contenant l'état à restaurer
     */
    public void setMemento(PerspectiveMemento memento) {
        this.facteurEchelle.set(memento.getFacteurEchelle());
        this.positionX.set(memento.getPositionX());
        this.positionY.set(memento.getPositionY());
        notifyObservers();
    }

    /**
     * Ajoute un observateur à la liste des observateurs
     * @param observer l'observateur à ajouter
     */
    @Override
    public void attach(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    /**
     * Supprime un observateur de la liste des observateurs
     * @param observer l'observateur à supprimer
     */
    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Notifie tous les observateurs d'un changement
     */
    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

// -------------------getter/setter-------------------------------------------------------------

    /**
     * Modifie le facteur d'échelle (zoom)
     * @param facteur le nouveau facteur d'échelle
     */
    public void setFacteurEchelle(double facteur) {
        this.facteurEchelle.set(facteur);
        notifyObservers();
    }

    /**
     * Modifie la position
     * @param x la nouvelle position X
     * @param y la nouvelle position Y
     */
    public void setPosition(int x, int y) {
        this.positionX.set(x);
        this.positionY.set(y);
        notifyObservers();
    }

    /**
     * @return l'image associée à cette perspective
     */
    public Image getImage() {
        return image;
    }

    /**
     * @return le facteur d'échelle actuel
     */
    public double getFacteurEchelle() {
        return facteurEchelle.get();
    }

    /**
     * @return la position X actuelle
     */
    public int getPositionX() {
        return positionX.get();
    }

    /**
     * @return la position Y actuelle
     */
    public int getPositionY() {
        return positionY.get();
    }

    /**
     * @return la propriété du facteur d'échelle
     */
    public DoubleProperty facteurEchelleProperty() {
        return facteurEchelle;
    }

    /**
     * @return la propriété de position X
     */
    public IntegerProperty positionXProperty() {
        return positionX;
    }

    /**
     * @return la propriété de position Y
     */
    public IntegerProperty positionYProperty() {
        return positionY;
    }
}