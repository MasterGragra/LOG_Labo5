package modele;

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
    private double facteurEchelle;
    private int positionX;
    private int positionY;
    private Image image;
    private final List<Observer> observers = new ArrayList<>();

    /**
     * Constructeur
     * @param image l'image associée à cette perspective
     */
    public Perspective(Image image) {
        this.image = image;
        this.facteurEchelle = 1.0;
        this.positionX = 0;
        this.positionY = 0;
    }

    /**
     * Crée un memento de l'état actuel de la perspective
     * @return un objet PerspectiveMemento contenant l'état actuel
     */
    public PerspectiveMemento createMemento() {
        return new PerspectiveMemento(facteurEchelle, positionX, positionY);
    }

    /**
     * Restaure l'état de la perspective à partir d'un memento
     * @param memento le memento contenant l'état à restaurer
     */
    public void setMemento(PerspectiveMemento memento) {
        this.facteurEchelle = memento.getFacteurEchelle();
        this.positionX = memento.getPositionX();
        this.positionY = memento.getPositionY();
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
            observer.update(this); // Valeur non utilisée ici
        }
    }

// -------------------getter/setter-------------------------------------------------------------

    /**
     * Modifie le facteur d'échelle (zoom)
     * @param facteur le nouveau facteur d'échelle
     */
    public void setFacteurEchelle(double facteur) {
        this.facteurEchelle = facteur;
        notifyObservers();
    }

    /**
     * Modifie la position
     * @param x la nouvelle position X
     * @param y la nouvelle position Y
     */
    public void setPosition(int x, int y) {
        this.positionX = x;
        this.positionY = y;
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
        return facteurEchelle;
    }

    /**
     * @return la position X actuelle
     */
    public int getPositionX() {
        return positionX;
    }

    /**
     * @return la position Y actuelle
     */
    public int getPositionY() {
        return positionY;
    }

}