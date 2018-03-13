package Model;

import Model.Commands.Command;

import java.util.Map;

public interface ApplicationI {
    public Command getCommands();
    public void clearApplication();
    public void addCommand(String name, Map params);
    public void undoCommand();
    public void redoCommand();
    public void saveWorld(String fileName);
    public void openWorld(String fileName);
}
