package Model;

import Model.Commands.Command;

import java.util.Map;

/**
 * ApplicationI describes general functions an Application should implement.
 */
public interface ApplicationI {
    /**
     * Get the commands of the application
     * @return A command object.
     */
    public Command getCommands();

    /**
     * Clears command lists of object.
     */
    public void clearApplication();

    /**
     * Adds a new command to the application.
     * @param name Name of command object.
     * @param params Attributes of the command.
     */
    public void addCommand(String name, Map params);

    /**
     * Undo a command from command history.
     */
    public void undoCommand();

    /**
     * Redo an undone command.
     */
    public void redoCommand();

    /**
     * Save the application to file.
     * @param fileName Filename of application.
     */
    public void saveWorld(String fileName);

    /**
     * Load application from file.
     * @param fileName Filename of the application.
     */
    public void openWorld(String fileName);
}
