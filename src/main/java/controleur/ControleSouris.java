package controleur;

import javafx.event.Event;

/**
 * Interface pour gérer les événements de la souris
 * dans le contexte d'une perspective
 */
public interface ControleSouris {

    // Méthode générique pour gérer les événements souris
    void gererEvenement(Event event, TypeEvenement type);

    // Enum pour les différents types d'événements
    enum TypeEvenement {
        CLIQUE, DRAG, RELACHE, SCROLL
    }
}
