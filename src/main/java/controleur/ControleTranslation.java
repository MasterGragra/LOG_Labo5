package controleur;

import commande.CommandManager;
import commande.CommandeTranslation;
import javafx.event.Event;
import javafx.scene.input.MouseEvent;
import modele.Perspective;

/**
 * Classe de contrôle pour la translation dans la perspective
 * Cette classe gère les événements de souris pour déplacer la perspective
 */
public class ControleTranslation implements ControleSouris {
    private Perspective perspective; // La perspective à contrôler
    private CommandManager commandManager; // Référence au gestionnaire de commandes
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
     * Méthode appelée lorsque la souris est utilisée pour déplacer la perspective
     * @param event L'événement de souris
     * @param type Le type d'événement
     */
    @Override
    public void gererEvenement(Event event, TypeEvenement type) {
        if (!(event instanceof MouseEvent mouseEvent)) {
            return;  // Ignorer les événements qui ne sont pas des MouseEvent
        }

        switch (type) {
            case CLIQUE:
                // Enregistrer les positions initiales
                sourisXInitiale = mouseEvent.getSceneX();
                sourisYInitiale = mouseEvent.getSceneY();
                perspectiveXInitiale = perspective.getPositionX();
                perspectiveYInitiale = perspective.getPositionY();
                break;

            case DRAG:
                // Calculer et appliquer le déplacement
                double deltaX = mouseEvent.getSceneX() - sourisXInitiale;
                double deltaY = mouseEvent.getSceneY() - sourisYInitiale;
                perspective.setPosition(
                        perspectiveXInitiale + (int)deltaX,
                        perspectiveYInitiale + (int)deltaY
                );
                break;

            case RELACHE:
                // Calculer la position finale
                deltaX = mouseEvent.getSceneX() - sourisXInitiale;
                deltaY = mouseEvent.getSceneY() - sourisYInitiale;

                // Appliquer le déplacement à la perspective
                int finalX = perspectiveXInitiale + (int)deltaX;
                int finalY = perspectiveYInitiale + (int)deltaY;

                // Ne créer une commande que si la position a changé
                if (finalX != perspectiveXInitiale || finalY != perspectiveYInitiale) {
                    CommandeTranslation command = new CommandeTranslation(perspective, finalX, finalY);

                    // Définir explicitement la position initiale
                    command.setPositionInitiale(perspectiveXInitiale, perspectiveYInitiale);
                    commandManager.executeCommand(command);
                }
                break;

            default:
                break;  // SCROLL et autres cas non gérés
        }
    }
}