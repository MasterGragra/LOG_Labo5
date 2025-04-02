package controleur;

import commande.CommandManager;
import commande.TranslationCommand;
import javafx.scene.input.MouseEvent;
import modele.Perspective;

public class ControleTranslation implements ControleSouris {
    private Perspective perspective;
    private CommandManager commandManager;
    private double startX, startY;  // Position initiale de la souris
    private int positionXInitiale, positionYInitiale;  // Position initiale de la perspective

    public ControleTranslation(Perspective perspective) {
        this.perspective = perspective;
        this.commandManager = CommandManager.getInstance();
    }

    @Override
    public void gererMousePressed(MouseEvent event) {
        // Enregistrer la position initiale de la souris
        startX = event.getSceneX();
        startY = event.getSceneY();

        // Enregistrer la position initiale de la perspective
        positionXInitiale = perspective.getPositionX();
        positionYInitiale = perspective.getPositionY();
    }

    @Override
    public void gererMouseDragged(MouseEvent event) {
        // Calculer le déplacement
        double deltaX = event.getSceneX() - startX;
        double deltaY = event.getSceneY() - startY;

        // Appliquer directement le déplacement (sans créer de commande)
        perspective.setPosition(
                positionXInitiale + (int)deltaX,
                positionYInitiale + (int)deltaY
        );
    }

    @Override
    public void gererMouseReleased(MouseEvent event) {
        // Créer et exécuter une commande seulement à la fin du mouvement
        int finalX = perspective.getPositionX();
        int finalY = perspective.getPositionY();

        if (finalX != positionXInitiale || finalY != positionYInitiale) {
            TranslationCommand command = new TranslationCommand(perspective, finalX, finalY);
            commandManager.executeCommand(command);
        }
    }
}