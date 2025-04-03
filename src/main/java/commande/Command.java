package commande;

/**
 * Interface pour les commandes
 * Chaque commande doit implémenter cette interface
 */
public interface Command {
    void execute();
    void undo();
}
