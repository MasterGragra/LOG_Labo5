package commande;

import memento.PerspectiveMediator;
import memento.PerspectiveMemento;
import modele.Perspective;

/**
 * Command to copy state of perspective into clipboard.
 */
public class CommandeCopier implements Command {

    private final Perspective source;
    private final boolean copierZoom;
    private final boolean copierCoords;


    public CommandeCopier(Perspective source, boolean copierZoom, boolean copierCoords) {
        this.source = source;
        this.copierZoom = copierZoom;
        this.copierCoords = copierCoords;
    }

    @Override
    public void execute() {
        PerspectiveMemento memento = source.createMemento();
        PerspectiveMediator.set(memento);

        System.out.println("Copied: zoom=" + source.getFacteurEchelle() +
                " coords=(" + source.getPositionX() + ", " + source.getPositionY() + ")");
    }

    @Override
    public void undo() {}
}
