package controleur;

import javafx.event.Event;
import commande.CommandManager;
import commande.CommandeZoom;
import javafx.scene.input.ScrollEvent;
import modele.Perspective;

/**
 * Classe de contrôle pour le zoom dans la perspective
 * Cette classe gère les événements de souris pour zoomer dans la perspective
 */
public class ControleZoom implements ControleSouris {
    private Perspective perspective; // La perspective à contrôler
    private CommandManager commandManager; // Référence au gestionnaire de commandes
    private double facteurZoomInitial; // Facteur de zoom initial

    /**
     * Constructeur
     * @param perspective La perspective à contrôler
     */
    public ControleZoom(Perspective perspective) {
        this.perspective = perspective;
        this.commandManager = CommandManager.getInstance();
    }

    /**
     * Méthode appelée lorsque la souris est utilisée pour zoomer
     * @param event L'événement de souris
     * @param type Le type d'événement
     */
    @Override
    public void gererEvenement(Event event, TypeEvenement type) {

        // Vérifier si l'événement est un événement de défilement
        if (event instanceof ScrollEvent scrollEvent && type == TypeEvenement.SCROLL) {

            // Stocker les valeurs initiales
            facteurZoomInitial = perspective.getFacteurEchelle();

            // Calculer le nouveau facteur de zoom
            double facteurZoomFinal = facteurZoomInitial + (scrollEvent.getDeltaY() * 0.01);

            // Créer et exécuter la commande
            CommandeZoom commandeZoom = new CommandeZoom(perspective, facteurZoomFinal);
            commandeZoom.setFacteurInitial(facteurZoomInitial);
            commandManager.executeCommand(commandeZoom);

            // Debug
            System.out.println("Commande de zoom exécutée. Initial: " + facteurZoomInitial +
                    ", Final: " + facteurZoomFinal);
        }
    }
}