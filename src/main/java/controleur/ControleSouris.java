package controleur;

import javafx.scene.input.MouseEvent;

public interface ControleSouris {

    void gererMousePressed(MouseEvent event);
    void gererMouseDragged(MouseEvent event);
    void gererMouseReleased(MouseEvent event);
}
