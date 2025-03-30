package controleur;

import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;

public interface ManipulationImageStrategie {

    void handleEvent(InputEvent event, ImageView imageView);
}
