package memento;

/**
 * Temporarily stocks state for perspective for copy-paste
 */
public class PerspectiveMediator implements Mediator {
    private static PerspectiveMemento memento = null;

    public static void set(PerspectiveMemento m) {
        memento = m;
    }

    public static PerspectiveMemento getMemento() {
        return memento;
    }

    public static boolean isEmpty() {
        return memento == null;
    }
}

