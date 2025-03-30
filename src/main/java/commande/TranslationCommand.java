package commande;

import modele.Perspective;

public class TranslationCommand implements Command {
    private Perspective perspective;
    private int ancientX;
    private int ancientY;
    private int nouveauX;
    private int nouveauY;

    public TranslationCommand(Perspective perspective, int nouveauX, int nouveauY) {
        this.perspective = perspective;
        this.nouveauX = nouveauX;
        this.nouveauY = nouveauY;
        this.ancientX = perspective.getPositionX(); // Save la position en X
        this.ancientY = perspective.getPositionY(); // Save la position en Y
    }

    @Override
    public void execute() {
        // Save position courrante avant de changer
        this.ancientX = perspective.getPositionX();
        this.ancientY = perspective.getPositionY();
        // Nouvelle position
        perspective.setPosition(nouveauX, nouveauY);
    }

    @Override
    public void undo() {
        perspective.setPosition(ancientX, ancientY);
    }
}