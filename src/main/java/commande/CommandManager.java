package commande;

import java.util.Stack;

public class CommandManager {
    private static CommandManager instance;
    private Stack<Command> commandesExecutees; // Executed commands stack
    private Stack<Command> commandesAnnulees;  // Undone commands stack

    // Constructeur privee
    private CommandManager() {
        commandesExecutees = new Stack<>();
        commandesAnnulees = new Stack<>();
    }

    // Singleton
    public static synchronized CommandManager getInstance() {
        if (instance == null) {
            instance = new CommandManager();
        }
        return instance;
    }

    // Execute nouvelle commande
    public void executeCommand(Command command) {
        command.execute();
        commandesExecutees.push(command);
        clearRedoStack(); // Clear le stack avant nouvelle commande
    }

    // Undo
    public void undo() {
        if (!commandesExecutees.isEmpty()) {
            Command command = commandesExecutees.pop();
            command.undo();
            commandesAnnulees.push(command);
        }
    }

    // Redo
    public void redo() {
        if (!commandesAnnulees.isEmpty()) {
            Command command = commandesAnnulees.pop();
            command.execute();
            commandesExecutees.push(command);
        }
    }

    // Clear stack
    public void clearRedoStack() {
        commandesAnnulees.clear();
    }
}