package controleur;

import commande.CommandManager;
import commande.TranslationCommand;
import javafx.scene.input.MouseEvent;
import modele.Perspective;

public class ControleTranslation implements ControleSouris {
    private Perspective perspective;
    private CommandManager commandManager;
    private double sourisXInitiale, sourisYInitiale;  // Position initiale de la souris
    private int perspectiveXInitiale, perspectiveYInitiale;  // Position initiale de la perspective

    public ControleTranslation(Perspective perspective) {
        this.perspective = perspective;
        this.commandManager = CommandManager.getInstance();
    }

    @Override
    public void gererMousePressed(MouseEvent event) {
        // Enregistrer la position initiale de la souris
        sourisXInitiale = event.getSceneX();
        sourisYInitiale = event.getSceneY();

        // Enregistrer la position initiale de la perspective
        perspectiveXInitiale = perspective.getPositionX();
        perspectiveYInitiale = perspective.getPositionY();
    }

    @Override
    public void gererMouseDragged(MouseEvent event) {
        // Calculer le déplacement
        double deltaX = event.getSceneX() - sourisXInitiale;
        double deltaY = event.getSceneY() - sourisYInitiale;

        // Appliquer directement le déplacement (sans créer de commande)
        perspective.setPosition(
                perspectiveXInitiale + (int)deltaX,
                perspectiveYInitiale + (int)deltaY
        );
    }

    @Override
    public void gererMouseReleased(MouseEvent event) {
        // Calculer la position finale
        double deltaX = event.getSceneX() - sourisXInitiale;
        double deltaY = event.getSceneY() - sourisYInitiale;

        int finalX = (int)(perspectiveXInitiale + deltaX);
        int finalY = (int)(perspectiveYInitiale + deltaY);

        // Ne créer une commande que si la position a changé
        if (finalX != perspectiveXInitiale || finalY != perspectiveYInitiale) {
            TranslationCommand command = new TranslationCommand(perspective, finalX, finalY);
            // Définir explicitement la position initiale
            command.setPositionInitiale(perspectiveXInitiale, perspectiveYInitiale);
            commandManager.executeCommand(command);
        }
    }
}