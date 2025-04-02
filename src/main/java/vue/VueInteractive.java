package vue;

import controleur.ControleSouris;
import modele.Perspective;

/**
 * Classe abstraite pour les vues interactives qui permettent
 * les interactions utilisateur via une stratégie de contrôle souris.
 */
public abstract class VueInteractive extends VueAbstraite {

    protected ControleSouris controleSouris;

    /**
     * Constructeur de la vue interactive
     * @param perspective La perspective à observer et manipuler
     */
    public VueInteractive(Perspective perspective) {
        super(perspective);

        // Activer le clipping pour empêcher le débordement
        activerClipping();
    }

    /**
     * Active le clipping pour empêcher l'image de déborder du conteneur
     */
    protected void activerClipping() {
        pane.layoutBoundsProperty().addListener((observable, oldBounds, newBounds) -> {
            // Créer un rectangle de clipping aux dimensions du pane
            javafx.scene.shape.Rectangle clip = new javafx.scene.shape.Rectangle(
                    newBounds.getWidth(), newBounds.getHeight()
            );

            // Appliquer le clip au conteneur
            pane.setClip(clip);
        });

        // Appliquer initialement si le pane a déjà une taille
        if (pane.getWidth() > 0 && pane.getHeight() > 0) {
            javafx.scene.shape.Rectangle clip = new javafx.scene.shape.Rectangle(
                    pane.getWidth(), pane.getHeight()
            );
            pane.setClip(clip);
        }
    }

    /**
     * Définit la stratégie de contrôle souris pour cette vue
     * @param controleSouris La nouvelle stratégie de contrôle
     */
    public void setControleSouris(ControleSouris controleSouris) {
        this.controleSouris = controleSouris;
    }

    /**
     * Récupère la stratégie de contrôle souris actuelle
     * @return La stratégie actuelle
     */
    public ControleSouris getControleSouris() {
        return controleSouris;
    }
}