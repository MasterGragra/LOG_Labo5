package controleur;

import commande.CommandManager;
import commande.ZoomCommand;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import modele.Perspective;

public class ControleZoom implements ManipulationImageStrategie {

    private ImageView imageView;
    private Perspective perspective;
    private double facteurZoomInitial;
    private double positionXInitiale, positionYInitiale;

    public ControleZoom(ImageView imageView, Perspective perspective) {
        this.imageView = imageView;
        this.perspective = perspective;
    }

    @Override
    public void gererMousePressed(MouseEvent event) {
        facteurZoomInitial = perspective.getFacteurEchelle();
        positionXInitiale = event.getSceneX();
        positionYInitiale = event.getSceneY();
    }

    @Override
    public void gererMouseDragged(MouseEvent event) {
        double deltaY = event.getSceneY() - positionYInitiale;
        double nouveauFacteur = facteurZoomInitial * (1 - deltaY / 500);

        // Limiter le facteur de zoom
        nouveauFacteur = Math.max(0.1, Math.min(5.0, nouveauFacteur));

        // Créer et exécuter la commande de zoom
        ZoomCommand command = new ZoomCommand(perspective, nouveauFacteur);
        CommandManager.getInstance().executeCommand(command);
    }

    @Override
    public void gererMouseReleased(MouseEvent event) {
        // Rien à faire ici
    }
}
