package commande;

import modele.Perspective;

/**
 * Commande pour traduire une perspective
 */
public class TranslationCommand implements Command {
    private Perspective perspective;
    private int ancientX;
    private int ancientY;
    private int nouveauX;
    private int nouveauY;

    /**
     * Constructeur de la commande de translation
     * @param perspective La perspective à traduire
     * @param nouveauX La nouvelle position en X
     * @param nouveauY La nouvelle position en Y
     */
    public TranslationCommand(Perspective perspective, int nouveauX, int nouveauY) {
        this.perspective = perspective;
        this.ancientX = perspective.getPositionX(); // Save la position en X
        this.ancientY = perspective.getPositionY(); // Save la position en Y
        this.nouveauX = nouveauX;
        this.nouveauY = nouveauY;
    }

    /**
     * Définit explicitement la position initiale
     * @param initialX Position X initiale
     * @param initialY Position Y initiale
     */
    public void setPositionInitiale(int initialX, int initialY) {
        this.ancientX = initialX;
        this.ancientY = initialY;
    }

    /**
     * Exécute la commande de translation
     */
    @Override
    public void execute() {
        perspective.setPosition(nouveauX, nouveauY);
    }

    /**
     * Annule la commande de translation
     */
    @Override
    public void undo() {
        perspective.setPosition(ancientX, ancientY);
    }
}