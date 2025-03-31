package controleur;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;


public class ImageController{
    @FXML private StackPane panneau1, panneau2, panneau3;
    @FXML private ImageView imageComplete, image2, image3;

    // Stratégies
    private final ManipulationImageStrategie controleZoom = new ControleZoom();
    private final ManipulationImageStrategie controleTranslation = new ControleTranslation();


    @FXML
    public void initialize() {
        System.out.println("ImageController initialized");

        setImageControls(panneau2, image2);
        setImageControls(panneau3, image3);
    }

    private void setImageControls(StackPane panneau, ImageView image){

        panneau.setPickOnBounds(true);
        image.setPickOnBounds(true);
        clipImageToPanel(panneau);

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

    /**
     * Méthode pour mettre un cadre virutel autour du panel ainsi l'image
     * dépasse pas le panel
     * @param panneau
     */
    private void clipImageToPanel(StackPane panneau){

        Rectangle clip1 = new Rectangle();
        clip1.widthProperty().bind(panneau.widthProperty());
        clip1.heightProperty().bind(panneau.heightProperty());
        panneau.setClip(clip1);
    }



}
