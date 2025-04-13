package vue;

import javafx.scene.layout.Pane;
import modele.Perspective;
import modele.Subject;

/**
 * Classe abstraite définissant une vue générique.
 * Implémente le pattern Observer pour être notifiée des changements du modèle.
 */
public abstract class VueAbstraite implements Observer {

    protected Perspective perspective;
    protected Pane pane;

    /**
     * Constructeur de la vue abstraite
     * @param perspective La perspective à observer
     */
    public VueAbstraite(Perspective perspective) {
        this.perspective = perspective;
        this.pane = new Pane();

        if (perspective != null) {
            // S'enregistrer comme observateur de la perspective
            perspective.attach(this);

            // Dessiner initialement la vue
            dessiner();
        }
    }

    /**
     * Méthode appelée lorsque le sujet observé est modifié
     * @param subject Le sujet modifié
     */
    @Override
    public void update(Subject subject) {
        if (subject == perspective) {
            dessiner();
        }
    }

    /**
     * Méthode abstraite à implémenter dans les sous-classes pour dessiner la vue
     */
    protected abstract void dessiner();

    /**
     * Récupère le panneau JavaFX associé à cette vue
     * @return Le panneau de la vue
     */
    public Pane getPane() {
        return pane;
    }

    public void renduInitial() {
        dessiner();
    }

    public Perspective getPerspective() {
        return perspective;
    }
}