package controleur;

import commande.CommandManager;
import commande.TranslationCommand;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import modele.Perspective;

public class ControleTranslation implements ManipulationImageStrategie {
    private ImageView imageView;
    private Perspective perspective;
    private int positionXInitiale, positionYInitiale;
    private int translateXInitial, translateYInitial;

    public ControleTranslation(ImageView imageView, Perspective perspective) {
        this.imageView = imageView;
        this.perspective = perspective;
    }

    @Override
    public void gererMousePressed(MouseEvent event) {
        positionXInitiale = (int) event.getSceneX();
        positionYInitiale = (int) event.getSceneY();
        translateXInitial = perspective.getPositionX();
        translateYInitial = perspective.getPositionY();
    }

    @Override
    public void gererMouseDragged(MouseEvent event) {
        int deltaX = (int) event.getSceneX() - positionXInitiale;
        int deltaY = (int) event.getSceneY() - positionYInitiale;

        int nouveauX = translateXInitial + deltaX;
        int nouveauY = translateYInitial + deltaY;

        // Créer et exécuter la commande de translation
        TranslationCommand command = new TranslationCommand(perspective, nouveauX, nouveauY);
        CommandManager.getInstance().executeCommand(command);
    }

    @Override
    public void gererMouseReleased(MouseEvent event) {
        // Rien à faire ici
    }
}
