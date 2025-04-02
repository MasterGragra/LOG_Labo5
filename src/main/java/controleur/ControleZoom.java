package controleur;

import javafx.scene.input.MouseEvent;
import commande.CommandManager;
import commande.ZoomCommand;
import modele.Perspective;

public class ControleZoom implements ControleSouris {
    private Perspective perspective;
    private CommandManager commandManager;
    private double facteurZoomInitial;
    private double positionXInitiale;
    private double positionYInitiale;

    public ControleZoom(Perspective perspective) {
        this.perspective = perspective;
        this.commandManager = CommandManager.getInstance();
    }

    @Override
    public void gererMousePressed(MouseEvent evt) {
        // Stocker les valeurs initiales pour pouvoir créer une commande plus tard
        facteurZoomInitial = perspective.getFacteurEchelle();
        positionXInitiale = perspective.getPositionX();
        positionYInitiale = perspective.getPositionY();
    }

    @Override
    public void gererMouseDragged(MouseEvent evt) {
        // Cette méthode n'est pas utilisée pour le zoom car nous utilisons le défilement
        // qui est géré directement dans ImageController
    }

    @Override
    public void gererMouseReleased(MouseEvent evt) {
        // Vérifier si le facteur de zoom a changé et créer une commande si nécessaire
        double facteurZoomFinal = perspective.getFacteurEchelle();

        if (facteurZoomInitial != facteurZoomFinal) {
            // Créer une commande ZoomCommand et l'exécuter via commandManager
            ZoomCommand zoomCommand = new ZoomCommand(perspective, facteurZoomFinal);
            commandManager.executeCommand(zoomCommand);
        }
    }

    /**
     * Méthode utilitaire pour effectuer un zoom
     * @param deltaY La valeur de déplacement en Y de la souris
     */
    public void effectuerZoom(double deltaY) {
        double facteurZoom = perspective.getFacteurEchelle();
        double delta = deltaY * 0.01; // Ajuster la sensibilité du zoom

        // Stocker les valeurs initiales
        facteurZoomInitial = facteurZoom;

        // Appliquer directement le zoom
        perspective.setFacteurEchelle(facteurZoom + delta);
    }
}