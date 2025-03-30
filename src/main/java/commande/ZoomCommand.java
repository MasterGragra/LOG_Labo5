package commande;

import modele.Perspective;

public class ZoomCommand implements Command {
    private Perspective perspective;
    private double ancienFacteur;
    private double nouveauFacteur;

    public ZoomCommand(Perspective perspective, double nouveauFacteur) {
        this.perspective = perspective;
        this.nouveauFacteur = nouveauFacteur;
        this.ancienFacteur = perspective.getFacteurEchelle(); // Save le zoom
    }

    @Override
    public void execute() {
        // Save zoom courrant
        this.ancienFacteur = perspective.getFacteurEchelle();
        // Nouveau zoom
        perspective.setFacteurEchelle(nouveauFacteur);
    }

    @Override
    public void undo() {
        perspective.setFacteurEchelle(ancienFacteur);
    }
}