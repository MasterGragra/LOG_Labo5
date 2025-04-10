package modele;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import memento.PerspectiveMemento;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import vue.Observer;

/**
 * Classe représentant une perspective d'affichage d'une image
 * Implémente Subject pour le pattern Observer et Serializable pour la persistance
 */
public class Perspective implements Subject, Serializable {
    private transient DoubleProperty facteurEchelle;
    private transient IntegerProperty positionX;
    private transient IntegerProperty positionY;
    private Image image;
    private transient List<Observer> observers = new ArrayList<>();
    private static final long serialVersionUID = 1L;

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
     * Méthode de sérialisation personnalisée pour écrire les propriétés
     * @param out le flux de sortie
     * @throws IOException en cas d'erreur d'écriture
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject(); // Écriture des champs normaux

        // Écrire les valeurs actuelles des propriétés
        out.writeDouble(facteurEchelle.get());
        out.writeInt(positionX.get());
        out.writeInt(positionY.get());
    }

    /**
     * Méthode de désérialisation personnalisée pour lire les propriétés
     * @param in le flux d'entrée
     * @throws IOException en cas d'erreur de lecture
     * @throws ClassNotFoundException si la classe n'est pas trouvée
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject(); // Lecture des champs normaux

        // Recréer les propriétés JavaFX
        facteurEchelle = new SimpleDoubleProperty(in.readDouble());
        positionX = new SimpleIntegerProperty(in.readInt());
        positionY = new SimpleIntegerProperty(in.readInt());

        // Réinitialiser la liste des observers
        observers = new ArrayList<>();
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