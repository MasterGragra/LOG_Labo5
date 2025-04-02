package controleur;

import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;

public interface ManipulationImageStrategie {

    void gererMousePressed(MouseEvent event);
    void gererMouseDragged(MouseEvent event);
    void gererMouseReleased(MouseEvent event);
}
