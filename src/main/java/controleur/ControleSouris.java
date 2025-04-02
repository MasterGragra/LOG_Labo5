package controleur;

import javafx.scene.input.MouseEvent;

/**
 * Interface pour gérer les événements de la souris
 * dans le contexte d'une perspective
 */
public interface ControleSouris {

    void gererMousePressed(MouseEvent event);
    void gererMouseDragged(MouseEvent event);
    void gererMouseReleased(MouseEvent event);
}
