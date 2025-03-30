package controleur;

import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;

public class ControleTranslation implements ManipulationImageStrategie {

    private double dragStartX, dragStartY;
    private double imageStartX, imageStartY;

    @Override
    public void handleEvent(InputEvent event, ImageView imageView) {

        if(event instanceof MouseEvent mouseEvent) {

            if(mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED
            && mouseEvent.isSecondaryButtonDown()) {

                dragStartX = mouseEvent.getSceneX();
                dragStartY = mouseEvent.getSceneY();
                imageStartX = mouseEvent.getSceneX();
                imageStartY = mouseEvent.getSceneY();
            }

            else if(mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED
            && mouseEvent.isSecondaryButtonDown()) {

                double deltaX = mouseEvent.getSceneX() - dragStartX;
                double deltaY = mouseEvent.getSceneY() - dragStartY;

                imageView.setTranslateX(imageStartX + deltaX);
                imageView.setTranslateY(imageStartY + deltaY);
            }
        }

    }
}
