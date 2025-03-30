package controleur;

import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.ScrollEvent;

public class ControleZoom implements ManipulationImageStrategie {

    private double zoomFactor = 1.0;
    private static final double ZOOM_INTENSITY = 0.1;

    @Override
    public void handleEvent(InputEvent event, ImageView imageView) {
        if(event instanceof ScrollEvent scrollEvent && scrollEvent.isControlDown()) {

            double zoom = scrollEvent.getDeltaY() > 0 ?
                    (1+ ZOOM_INTENSITY) : (1- ZOOM_INTENSITY);

            zoomFactor *= zoom;
            imageView.setScaleX(zoomFactor);
            imageView.setScaleY(zoomFactor);
            scrollEvent.consume();
        }
    }
}
