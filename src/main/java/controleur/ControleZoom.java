package controleur;

import javafx.scene.input.MouseEvent;
import commande.CommandManager;
import commande.ZoomCommand;
import modele.Perspective;

/**
 * Classe de contrôle pour le zoom dans la perspective
 * Cette classe gère les événements de souris pour effectuer un zoom avant ou arrière
 */
public class ControleZoom implements ControleSouris {
    private Perspective perspective;
    private CommandManager commandManager;
    private double facteurZoomInitial;
    private double positionXInitiale;
    private double positionYInitiale;

    /**
     * Constructeur
     * @param perspective La perspective à contrôler
     */
    public ControleZoom(Perspective perspective) {
        this.perspective = perspective;
        this.commandManager = CommandManager.getInstance();
    }

    /**
     * Méthode appelée lorsque la souris est pressée
     * @param evt L'événement de souris
     */
    @Override
    public void gererMousePressed(MouseEvent evt) {
        // Stocker les valeurs initiales pour pouvoir créer une commande plus tard
        facteurZoomInitial = perspective.getFacteurEchelle();
        positionXInitiale = perspective.getPositionX();
        positionYInitiale = perspective.getPositionY();
    }

    /**
     * Méthode appelée lorsque la souris est déplacée
     * @param evt L'événement de souris
     */
    @Override
    public void gererMouseDragged(MouseEvent evt) {
        // Cette méthode n'est pas utilisée pour le zoom car nous utilisons le défilement
        // qui est géré directement dans ImageController
    }

    /**
     * Méthode appelée lorsque la souris est relâchée
     * @param evt L'événement de souris
     */
    @Override
    public void gererMouseReleased(MouseEvent evt) {
        double facteurZoomFinal = perspective.getFacteurEchelle();

        // Vérifier si le facteur de zoom a changé
        if (facteurZoomInitial != facteurZoomFinal) {
            ZoomCommand zoomCommand = new ZoomCommand(perspective, facteurZoomFinal);
            // Définir explicitement le facteur initial
            zoomCommand.setFacteurInitial(facteurZoomInitial);
            commandManager.executeCommand(zoomCommand);
        }
    }

    /**
     * Méthode utilitaire pour effectuer un zoom
     * @param deltaY La valeur de déplacement en Y de la souris
     */
    public void effectuerZoom(double deltaY) {
        // Stocker les valeurs initiales avant modification
        facteurZoomInitial = perspective.getFacteurEchelle();

        // Calculer le nouveau facteur de zoom
        double facteurZoomFinal = facteurZoomInitial + (deltaY * 0.01);

        // Appliquer directement le zoom
        perspective.setFacteurEchelle(facteurZoomFinal);

        // Créer et exécuter la commande
        ZoomCommand zoomCommand = new ZoomCommand(perspective, facteurZoomFinal);
        zoomCommand.setFacteurInitial(facteurZoomInitial);

        // Enregistrer la commande dans le CommandManager
        commandManager.executeCommand(zoomCommand);

        // Debug
        System.out.println("Commande de zoom exécutée. Initial: " + facteurZoomInitial +
                ", Final: " + facteurZoomFinal);
    }
}