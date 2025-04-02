package controleur;

import commande.CommandManager;
import commande.TranslationCommand;
import javafx.scene.input.MouseEvent;
import modele.Perspective;

/**
 * Classe de contrôle pour la translation dans la perspective
 * Cette classe gère les événements de souris pour déplacer la perspective
 */
public class ControleTranslation implements ControleSouris {
    private Perspective perspective;
    private CommandManager commandManager;
    private double sourisXInitiale, sourisYInitiale;  // Position initiale de la souris
    private int perspectiveXInitiale, perspectiveYInitiale;  // Position initiale de la perspective

    /**
     * Constructeur
     * @param perspective La perspective à contrôler
     */
    public ControleTranslation(Perspective perspective) {
        this.perspective = perspective;
        this.commandManager = CommandManager.getInstance();
    }

    /**
     * Méthode appelée lorsque la souris est pressée
     * @param event L'événement de souris
     */
    @Override
    public void gererMousePressed(MouseEvent event) {
        // Enregistrer la position initiale de la souris
        sourisXInitiale = event.getSceneX();
        sourisYInitiale = event.getSceneY();

        // Enregistrer la position initiale de la perspective
        perspectiveXInitiale = perspective.getPositionX();
        perspectiveYInitiale = perspective.getPositionY();
    }

    /**
     * Méthode appelée lorsque la souris est déplacée
     * @param event L'événement de souris
     */
    @Override
    public void gererMouseDragged(MouseEvent event) {
        // Calculer le déplacement
        double deltaX = event.getSceneX() - sourisXInitiale;
        double deltaY = event.getSceneY() - sourisYInitiale;

        // Appliquer directement le déplacement
        perspective.setPosition(
                perspectiveXInitiale + (int)deltaX,
                perspectiveYInitiale + (int)deltaY
        );
    }

    /**
     * Méthode appelée lorsque la souris est relâchée
     * @param event L'événement de souris
     */
    @Override
    public void gererMouseReleased(MouseEvent event) {
        // Calculer la position finale
        double deltaX = event.getSceneX() - sourisXInitiale;
        double deltaY = event.getSceneY() - sourisYInitiale;

        // Appliquer le déplacement à la perspective
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