package commande;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import java.util.Stack;

public class CommandManager {
    private static CommandManager instance;
    private Stack<Command> commandesExecutees;
    private Stack<Command> commandesAnnulees;

    // Constante pour limiter la taille des piles
    private static final int TAILLE_MAX_HISTORIQUE = 50;

    // Propriétés observables pour l'activation/désactivation des boutons (User friendly)
    private final ReadOnlyBooleanWrapper canUndoProperty = new ReadOnlyBooleanWrapper(false);
    private final ReadOnlyBooleanWrapper canRedoProperty = new ReadOnlyBooleanWrapper(false);

    /**
     * Constructeur privé pour le singleton
     */
    private CommandManager() {
        commandesExecutees = new Stack<>();
        commandesAnnulees = new Stack<>();
    }

    /**
     * Méthode pour obtenir l'instance unique de CommandManager
     * @return l'instance unique de CommandManager
     */
    public static synchronized CommandManager getInstance() {

        // Vérifier si l'instance n'est pas déjà créée
        if (instance == null) {
            instance = new CommandManager();
        }
        return instance;
    }

    /**
     * Exécute une commande et l'ajoute à la pile des commandes exécutées
     * @param command la commande à exécuter
     */
    public void executeCommand(Command command) {

        // Vérifier si la commande est valide
        if (command != null) {
            command.execute();
            commandesExecutees.push(command);

            // Limiter la taille de la pile pour éviter une consommation excessive de mémoire
            if (commandesExecutees.size() > TAILLE_MAX_HISTORIQUE) {
                // Supprimer la commande la plus ancienne
                commandesExecutees.removeFirst();
            }

            // Toujours vider la pile d'annulation après une nouvelle commande
            clearRedoStack();

            // Mise à jour des propriétés observables
            updateProperties();

            // Debug
            System.out.println("Commande exécutée. Stack size: " + commandesExecutees.size());
        }
    }

    /**
     * Annule la dernière commande exécutée
     */
    public void undo() {

        // Vérifier si la pile des commandes exécutées n'est pas vide
        if (!commandesExecutees.isEmpty()) {
            Command command = commandesExecutees.pop();
            command.undo();
            commandesAnnulees.push(command);

            // Limiter la taille de la pile d'annulation
            if (commandesAnnulees.size() > TAILLE_MAX_HISTORIQUE) {
                commandesAnnulees.removeFirst();
            }

            // Mise à jour des propriétés observables
            updateProperties();

            // Debug
            System.out.println("Commande annulée. Undo stack: " + commandesExecutees.size() +
                    ", Redo stack: " + commandesAnnulees.size());
        }
    }

    /**
     * Rétablit la dernière commande annulée
     */
    public void redo() {

        // Vérifier si la pile des commandes annulées n'est pas vide
        if (!commandesAnnulees.isEmpty()) {
            Command command = commandesAnnulees.pop();
            command.execute();
            commandesExecutees.push(command);

            // Mise à jour des propriétés observables
            updateProperties();

            // Debug
            System.out.println("Commande rétablie. Undo stack: " + commandesExecutees.size() +
                    ", Redo stack: " + commandesAnnulees.size());
        }
    }

    /**
     * Efface la pile des commandes exécutées
     */
    public void clearRedoStack() {
        commandesAnnulees.clear();
        updateProperties();
    }

    /**
     * Méthodes pour vérifier si undo sont disponibles
     */
    public boolean canUndo() {
        return !commandesExecutees.isEmpty();
    }

    /**
     * Méthodes pour vérifier si redo sont disponibles
     */
    public boolean canRedo() {
        return !commandesAnnulees.isEmpty();
    }

    /**
     * Retourne la propriété observable pour vérifier si l'annulation est possible
     */
    public ReadOnlyBooleanProperty canUndoProperty() {
        return canUndoProperty.getReadOnlyProperty();
    }

    /**
     * Retourne la propriété observable pour vérifier si le rétablissement est possible
     */
    public ReadOnlyBooleanProperty canRedoProperty() {
        return canRedoProperty.getReadOnlyProperty();
    }

    /**
     * Met à jour les propriétés observables en fonction de l'état des piles
     */
    private void updateProperties() {
        canUndoProperty.set(!commandesExecutees.isEmpty());
        canRedoProperty.set(!commandesAnnulees.isEmpty());
    }

    // Pour déboguer
    public int getUndoStackSize() {
        return commandesExecutees.size();
    }

    public int getRedoStackSize() {
        return commandesAnnulees.size();
    }
}