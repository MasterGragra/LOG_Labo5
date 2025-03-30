package controleur;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;



public class ImageController{
    @FXML private StackPane panneau1, panneau2, panneau3;
    @FXML private ImageView imageComplete, image2, image3;

    // Stratégies
    private final ManipulationImageStrategie controleZoom = new ControleZoom();
    private final ManipulationImageStrategie controleTranslation = new ControleTranslation();


    @FXML
    public void initialize() {
        setImageControls(panneau2, image2);
        setImageControls(panneau3, image3);
    }

    private void setImageControls(StackPane panneau, ImageView image){

        panneau.setPickOnBounds(true);
        image.setPickOnBounds(true);

        panneau.setOnScroll(event -> {
            controleZoom.handleEvent(event, image);
            event.consume();
        });

        panneau.setOnMousePressed(event -> {
            controleTranslation.handleEvent(event,image);
            event.consume();
        });

        panneau.setOnMouseDragged(event -> {
            controleTranslation.handleEvent(event,image);
            event.consume();
        });
        panneau.setOnMouseEntered(e -> System.out.println("Mouse entered pane"));
        panneau.setOnMouseExited(e -> System.out.println("Mouse exited pane"));

    }



}
