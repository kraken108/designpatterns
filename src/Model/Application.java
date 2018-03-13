package Model;

import Model.Commands.Command;

import java.util.List;
import java.util.Map;

/**
 * Application describes how a general application should work.
 */
abstract public class Application implements ApplicationI {
    private static int INDEX_START = 1;
    protected Command commands;

    /**
     * Get the history of commands of this application
     *
     * @return The command.
     */
    public Command getCommands() {
        return commands;
    }

    /**
     * Constructor
     */
    public Application() {
        commands = new Command();
    }

    /**
     * Clears the history of all commands in this application.
     */
    public void clearApplication() {
        commands.clearCommands();
    }

    /**
     * Add a command to this application.
     *
     * @param command Name of command to be added.
     * @param params  Attributes the command should possess.
     */
    public abstract void addCommand(String command, Map params);

    /**
     * Adds a command to this application.
     *
     * @param c The command to be added.
     */
    public void addCommand(Command c) {
        if (c != null) {
            commands.addCommand(c);
        }
    }

    /**
     * Removes the latest command from history.
     */
    public void undoCommand(){
        commands.undoCommand(INDEX_START);
    }

    /**
     * Redo the latest undone command.
     */
    public void redoCommand() {
        commands.redoCommand();
    }

    /**
     * Saves the application to file.
     * @param fileName The filename to write application data to.
     */
    public abstract void saveWorld(String fileName);

    /**
     * Loads an application from file to the model.
     * @param fileName The filename to read from.
     */
    public abstract void openWorld(String fileName);


}
