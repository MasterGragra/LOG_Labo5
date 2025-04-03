package commande;

import modele.Perspective;

/**
 * Commande pour zoomer sur une perspective
 */
public class ZoomCommand implements Command {
    private Perspective perspective;
    private double ancienFacteur;
    private double nouveauFacteur;

    /**
     * Constructeur de la commande de zoom
     * @param perspective La perspective à zoomer
     * @param nouveauFacteur Le nouveau facteur de zoom
     */
    public ZoomCommand(Perspective perspective, double nouveauFacteur) {
        this.perspective = perspective;
        this.ancienFacteur = perspective.getFacteurEchelle(); // Save le zoom
        this.nouveauFacteur = nouveauFacteur;
    }

    /**
     * Définit explicitement le facteur de zoom initial
     * @param facteurInitial Facteur de zoom initial
     */
    public void setFacteurInitial(double facteurInitial) {
        this.ancienFacteur = facteurInitial;
    }

    /**
     * Exécute la commande de zoom
     */
    @Override
    public void execute() {
        perspective.setFacteurEchelle(nouveauFacteur);
    }

    /**
     * Annule la commande de zoom
     */
    @Override
    public void undo() {
        perspective.setFacteurEchelle(ancienFacteur);
    }
}