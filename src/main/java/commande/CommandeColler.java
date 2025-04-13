package commande;

import application.GestionnaireInterface;
import memento.PerspectiveClipboard;
import memento.PerspectiveMemento;
import modele.Perspective;

/**
 * Command to paste state of perspective from clipboard.
 */
public class CommandeColler implements Command {

    private final Perspective cible;
    private PerspectiveMemento mementoAvant;

    public CommandeColler(Perspective cible) {
        this.cible = cible;
    }

    @Override
    public void execute() {
        if (PerspectiveClipboard.isEmpty()) return;

        mementoAvant = cible.createMemento();
        PerspectiveMemento memento = PerspectiveClipboard.getMemento();

        if (GestionnaireInterface.getInstance().copierZoom()) {
            cible.setFacteurEchelle(memento.getFacteurEchelle());
        }
        if (GestionnaireInterface.getInstance().copierCoords()) {
            cible.setPosition(memento.getPositionX(), memento.getPositionY());
        }

        cible.notifyObservers();
    }

    @Override
    public void undo() {
        if (mementoAvant != null) {
            cible.setMemento(mementoAvant);
        }
    }
}
